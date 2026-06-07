package com.profitai.storage.infrastructure.config;

import com.profitai.storage.application.usecase.DownloadObjectUseCase;
import com.profitai.storage.application.usecase.DownloadObjectUseCaseImpl;
import com.profitai.storage.application.usecase.UploadObjectUseCase;
import com.profitai.storage.application.usecase.UploadObjectUseCaseImpl;
import com.profitai.storage.domain.port.ObjectStoragePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3UseCaseConfig {

	@Bean
	public UploadObjectUseCase uploadObjectUseCase(ObjectStoragePort objectStoragePort) {
		return new UploadObjectUseCaseImpl(objectStoragePort);
	}

	@Bean
	public DownloadObjectUseCase downloadObjectUseCase(ObjectStoragePort objectStoragePort) {
		return new DownloadObjectUseCaseImpl(objectStoragePort);
	}
}
