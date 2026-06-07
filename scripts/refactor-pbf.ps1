$ErrorActionPreference = 'Stop'

# Replacement rules: most-specific FIRST.
# Each entry is @(regex, replacement) for package text;
# the path-level mirror is built from the same rules with slashes.
$pkgRules = @(
    @('com\.profitai\.application\.auth\.usecases',            'com.profitai.auth.application.usecase'),
    @('com\.profitai\.application\.s3\.usecases',              'com.profitai.storage.application.usecase'),
    @('com\.profitai\.infrastructure\.auth\.persistence\.jpa', 'com.profitai.auth.infrastructure.persistence'),
    @('com\.profitai\.infrastructure\.auth\.controller',       'com.profitai.auth.infrastructure.web'),
    @('com\.profitai\.infrastructure\.s3\.controller',         'com.profitai.storage.infrastructure.web'),
    @('com\.profitai\.domain\.auth',                           'com.profitai.auth.domain'),
    @('com\.profitai\.application\.auth',                      'com.profitai.auth.application'),
    @('com\.profitai\.infrastructure\.auth',                   'com.profitai.auth.infrastructure'),
    @('com\.profitai\.domain\.s3',                             'com.profitai.storage.domain'),
    @('com\.profitai\.application\.s3',                        'com.profitai.storage.application'),
    @('com\.profitai\.infrastructure\.s3',                     'com.profitai.storage.infrastructure'),
    @('com\.profitai\.infrastructure\.config',                 'com.profitai.shared.config'),
    @('com\.profitai\.infrastructure\.exception',              'com.profitai.shared.exception')
)

$pathRules = @(
    @('com/profitai/application/auth/usecases',            'com/profitai/auth/application/usecase'),
    @('com/profitai/application/s3/usecases',              'com/profitai/storage/application/usecase'),
    @('com/profitai/infrastructure/auth/persistence/jpa',  'com/profitai/auth/infrastructure/persistence'),
    @('com/profitai/infrastructure/auth/controller',       'com/profitai/auth/infrastructure/web'),
    @('com/profitai/infrastructure/s3/controller',         'com/profitai/storage/infrastructure/web'),
    @('com/profitai/domain/auth',                          'com/profitai/auth/domain'),
    @('com/profitai/application/auth',                     'com/profitai/auth/application'),
    @('com/profitai/infrastructure/auth',                  'com/profitai/auth/infrastructure'),
    @('com/profitai/domain/s3',                            'com/profitai/storage/domain'),
    @('com/profitai/application/s3',                       'com/profitai/storage/application'),
    @('com/profitai/infrastructure/s3',                    'com/profitai/storage/infrastructure'),
    @('com/profitai/infrastructure/config',                'com/profitai/shared/config'),
    @('com/profitai/infrastructure/exception',             'com/profitai/shared/exception')
)

$repoRoot = (Get-Location).Path

function To-Forward($p) { $p.Replace('\','/') }

# Step 1: git mv every .java file whose path matches a rule.
$srcFiles = Get-ChildItem -Path "src/main/java/com/profitai" -Recurse -Filter *.java -File
foreach ($f in $srcFiles) {
    $rel = To-Forward ($f.FullName.Substring($repoRoot.Length + 1))
    $target = $rel
    foreach ($r in $pathRules) {
        if ($target -match $r[0]) {
            $target = $target -replace $r[0], $r[1]
            break
        }
    }
    if ($target -ne $rel) {
        $targetDir = Split-Path $target -Parent
        if (-not (Test-Path $targetDir)) {
            New-Item -ItemType Directory -Path $targetDir -Force | Out-Null
        }
        Write-Host "mv  $rel  ->  $target"
        & git mv -- $rel $target
        if ($LASTEXITCODE -ne 0) { throw "git mv failed for $rel" }
    }
}

# Step 2: rewrite package declarations and imports across ALL .java files.
$utf8NoBom = New-Object System.Text.UTF8Encoding($false)
$allJava = Get-ChildItem -Path "src" -Recurse -Filter *.java -File
foreach ($f in $allJava) {
    $content = [System.IO.File]::ReadAllText($f.FullName, $utf8NoBom)
    $orig = $content
    foreach ($r in $pkgRules) {
        $content = [regex]::Replace($content, $r[0], $r[1])
    }
    if ($content -ne $orig) {
        [System.IO.File]::WriteAllText($f.FullName, $content, $utf8NoBom)
        Write-Host "rewrote $(To-Forward $f.FullName.Substring($repoRoot.Length + 1))"
    }
}

# Step 3: remove now-empty legacy directories.
$legacyRoots = @(
    "src/main/java/com/profitai/application",
    "src/main/java/com/profitai/domain",
    "src/main/java/com/profitai/infrastructure"
)
foreach ($d in $legacyRoots) {
    if (Test-Path $d) {
        Get-ChildItem -Path $d -Recurse -Directory |
            Sort-Object -Property FullName -Descending |
            ForEach-Object {
                if ((Get-ChildItem $_.FullName -Force | Measure-Object).Count -eq 0) {
                    Remove-Item $_.FullName -Force
                }
            }
        if ((Get-ChildItem $d -Force | Measure-Object).Count -eq 0) {
            Remove-Item $d -Force
        }
    }
}

Write-Host "`nRefactor complete."
