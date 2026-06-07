package com.profitai.storage.infrastructure.web;

import com.profitai.storage.application.usecase.DownloadObjectUseCase;
import com.profitai.storage.application.usecase.UploadObjectUseCase;
import com.profitai.storage.domain.valueobject.StoredObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/s3")
public class S3Controller {

	private final UploadObjectUseCase uploadObjectUseCase;
	private final DownloadObjectUseCase downloadObjectUseCase;

	public S3Controller(UploadObjectUseCase uploadObjectUseCase, DownloadObjectUseCase downloadObjectUseCase) {
		this.uploadObjectUseCase = uploadObjectUseCase;
		this.downloadObjectUseCase = downloadObjectUseCase;
	}

	@PostMapping("/upload")
	public ResponseEntity<UploadResponse> upload(@RequestParam("file") MultipartFile file) throws IOException {
		String key = uploadObjectUseCase.execute(file.getOriginalFilename(), file.getContentType(), file.getBytes());
		return ResponseEntity.ok(new UploadResponse(key));
	}

	@GetMapping("/download/{filename}")
	public ResponseEntity<byte[]> download(@PathVariable String filename) {
		StoredObject storedObject = downloadObjectUseCase.execute(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType(storedObject.contentType() == null
						? MediaType.APPLICATION_OCTET_STREAM_VALUE
						: storedObject.contentType()))
				.body(storedObject.bytes());
	}

	public record UploadResponse(String key) {
	}
}
