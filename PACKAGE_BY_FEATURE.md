# Refatoração: Package by Feature

Proposta de reorganização da estrutura de pacotes em `src/main/java/com/profitai`,
saindo do modelo **package by layer** (atual) para **package by feature**,
mantendo a separação interna em camadas da Clean Architecture / Hexagonal.

---

## 1. Princípios

1. **Feature first, layer second.** O primeiro nível abaixo de `com.profitai`
   passa a ser a feature de negócio (`auth`, `storage`, ...), não a camada
   técnica (`application`, `domain`, `infrastructure`).
2. **Cada feature é autocontida.** Tudo que pertence à feature
   (use cases, entidades, ports, adapters, controllers, DTOs) vive dentro do
   pacote dela.
3. **Camadas preservadas internamente.** Dentro de cada feature mantemos
   `domain` / `application` / `infrastructure`, então a dependência continua
   apontando para dentro (infra → application → domain).
4. **Cross-cutting fica em `shared`.** Configuração global, exception handler
   global, util etc. ficam num pacote neutro fora das features.
5. **Sem dependência cruzada entre features.** Se `feature A` precisar de
   `feature B`, ela depende apenas dos contratos públicos
   (port / use case interface) de B — nunca de classes internas.

---

## 2. Estrutura atual (package by layer)

```
com.profitai
├── ProfitAiApplication.java
├── application/
│   ├── auth/          (dto, mapper, usecases)
│   └── s3/            (usecases)
├── domain/
│   ├── auth/          (entity, exception, port, repository, valueobject)
│   └── s3/            (port, valueobject)
└── infrastructure/
    ├── auth/          (config, controller, persistence, security)
    ├── s3/            (adapter, config, controller)
    ├── config/        (CorsConfig, SecurityConfig)         ← global
    └── exception/     (ErrorResponse, GlobalExceptionHandler) ← global
```

**Dor:** para entender a feature `auth` é preciso navegar em 3 pacotes
distintos. Adicionar uma nova feature implica espalhar arquivos em 3 árvores.

---

## 3. Estrutura proposta (package by feature)

```
com.profitai
├── ProfitAiApplication.java
│
├── auth/                                 ← feature: autenticação e usuário
│   ├── domain/
│   │   ├── entity/         User.java
│   │   ├── valueobject/    Email.java, Password.java
│   │   ├── exception/      InvalidCredentialsException.java, ...
│   │   ├── port/           PasswordEncoder.java, TokenProvider.java
│   │   └── repository/     UserRepository.java
│   ├── application/
│   │   ├── dto/            LoginRequest.java, LoginResponse.java, ...
│   │   ├── mapper/         UserMapper.java
│   │   └── usecase/        LoginUseCase(+Impl).java, RegisterUserUseCase(+Impl).java, ...
│   └── infrastructure/
│       ├── config/         AuthUseCaseConfig.java
│       ├── web/            AuthController.java
│       ├── security/       JwtAuthenticationFilter.java,
│       │                   JwtTokenProvider.java,
│       │                   JwtTokenProviderAdapter.java,
│       │                   SpringPasswordEncoderAdapter.java
│       └── persistence/
│           ├── adapter/    UserRepositoryAdapter.java
│           ├── entity/     UserEntity.java
│           ├── mapper/     UserEntityMapper.java
│           └── repository/ JpaUserRepository.java
│
├── storage/                              ← feature: object storage (S3)
│   ├── domain/
│   │   ├── valueobject/    StoredObject.java
│   │   └── port/           ObjectStoragePort.java
│   ├── application/
│   │   └── usecase/        UploadObjectUseCase(+Impl).java,
│   │                       DownloadObjectUseCase(+Impl).java
│   └── infrastructure/
│       ├── config/         S3Config.java, S3UseCaseConfig.java
│       ├── web/            S3Controller.java
│       └── adapter/        S3ObjectStorageAdapter.java
│
└── shared/                               ← cross-cutting (sem regra de negócio)
    ├── config/             CorsConfig.java, SecurityConfig.java
    └── exception/          ErrorResponse.java, GlobalExceptionHandler.java
```

> Renomear `s3` → `storage` é opcional, mas recomendado: feature é o conceito
> ("armazenamento de objetos"), `s3` é só o provedor atual. Se preferir não
> renomear agora, basta manter `s3/`.

---

## 4. Mapeamento de migração (de → para)

### Feature `auth`

| De | Para |
|---|---|
| `domain/auth/entity/User.java`                     | `auth/domain/entity/User.java` |
| `domain/auth/valueobject/Email.java`               | `auth/domain/valueobject/Email.java` |
| `domain/auth/valueobject/Password.java`            | `auth/domain/valueobject/Password.java` |
| `domain/auth/exception/*.java`                     | `auth/domain/exception/*.java` |
| `domain/auth/port/PasswordEncoder.java`            | `auth/domain/port/PasswordEncoder.java` |
| `domain/auth/port/TokenProvider.java`              | `auth/domain/port/TokenProvider.java` |
| `domain/auth/repository/UserRepository.java`       | `auth/domain/repository/UserRepository.java` |
| `application/auth/dto/*.java`                      | `auth/application/dto/*.java` |
| `application/auth/mapper/UserMapper.java`          | `auth/application/mapper/UserMapper.java` |
| `application/auth/usecases/*.java`                 | `auth/application/usecase/*.java` |
| `infrastructure/auth/config/AuthUseCaseConfig.java`| `auth/infrastructure/config/AuthUseCaseConfig.java` |
| `infrastructure/auth/controller/AuthController.java` | `auth/infrastructure/web/AuthController.java` |
| `infrastructure/auth/persistence/jpa/**`           | `auth/infrastructure/persistence/**` |
| `infrastructure/auth/security/*.java`              | `auth/infrastructure/security/*.java` |

### Feature `storage` (antiga `s3`)

| De | Para |
|---|---|
| `domain/s3/port/ObjectStoragePort.java`            | `storage/domain/port/ObjectStoragePort.java` |
| `domain/s3/valueobject/StoredObject.java`          | `storage/domain/valueobject/StoredObject.java` |
| `application/s3/usecases/*.java`                   | `storage/application/usecase/*.java` |
| `infrastructure/s3/adapter/S3ObjectStorageAdapter.java` | `storage/infrastructure/adapter/S3ObjectStorageAdapter.java` |
| `infrastructure/s3/config/*.java`                  | `storage/infrastructure/config/*.java` |
| `infrastructure/s3/controller/S3Controller.java`   | `storage/infrastructure/web/S3Controller.java` |

### Shared (cross-cutting)

| De | Para |
|---|---|
| `infrastructure/config/CorsConfig.java`            | `shared/config/CorsConfig.java` |
| `infrastructure/config/SecurityConfig.java`        | `shared/config/SecurityConfig.java` |
| `infrastructure/exception/ErrorResponse.java`      | `shared/exception/ErrorResponse.java` |
| `infrastructure/exception/GlobalExceptionHandler.java` | `shared/exception/GlobalExceptionHandler.java` |

### Convenções de renomeação

- `usecases/` (plural) → `usecase/` (singular, padrão Java).
- `controller/` → `web/` (deixa claro que é a borda HTTP, não a única opção
  de adapter de entrada).
- `infrastructure/auth/persistence/jpa/` → `auth/infrastructure/persistence/`
  (o nível `jpa` só faz sentido se houver mais de uma tecnologia de
  persistência convivendo).

---

## 5. Cross-feature: como `SecurityConfig` referencia `auth`

`shared/config/SecurityConfig` hoje importa `JwtAuthenticationFilter` da
feature `auth`. Isso continua válido: o **shared** depende das features
porque ele é o composition root da aplicação (onde tudo é "ligado"). O que
NÃO pode acontecer é uma feature depender da outra por classes internas.

Regra prática de dependência:

```
shared  ──▶  feature.infrastructure  ──▶  feature.application  ──▶  feature.domain
                                                                       ▲
   outra feature, se necessária, só via port/use case da feature alvo ─┘
```

---

## 6. Checklist de execução

1. Criar os pacotes novos vazios (`auth/`, `storage/`, `shared/`).
2. Mover arquivos um pacote por vez, usando o refactor da IDE para que
   `package` e `import` sejam atualizados automaticamente.
3. Atualizar referências em `application.yml` / `application.properties`
   caso alguma classe seja referenciada por FQN (ex.: `logging.level`).
4. Atualizar testes em `src/test/java/com/profitai/**` espelhando a nova
   árvore.
5. Rodar `./mvnw clean verify` e o Spotless.
6. Conferir que os endpoints continuam respondendo nos mesmos paths
   (`/v1/auth/**`, `/v1/s3/**`) — paths são definidos nos controllers,
   não mudam por mudança de pacote.
7. Commit atômico por feature movida (`refactor(auth): package by feature`,
   `refactor(storage): package by feature`, `refactor(shared): extract cross-cutting`).

---

## 7. Benefícios esperados

- **Coesão alta:** tudo da feature em um lugar; remover/extrair uma feature
  é mover uma pasta.
- **Onboarding mais rápido:** novo dev abre `auth/` e vê o domínio inteiro.
- **Escala melhor:** adicionar `billing`, `report`, etc. não polui as
  árvores `application/`, `domain/`, `infrastructure/`.
- **Modularização futura mais fácil:** cada feature pode virar um módulo
  Maven separado sem reescrever a árvore.
