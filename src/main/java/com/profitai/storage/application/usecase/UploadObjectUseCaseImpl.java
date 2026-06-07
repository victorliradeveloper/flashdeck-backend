package com.profitai.storage.application.usecase;

import com.profitai.storage.domain.port.ObjectStoragePort;

import java.util.UUID;

public class UploadObjectUseCaseImpl implements UploadObjectUseCase {

	private final ObjectStoragePort objectStoragePort;

	public UploadObjectUseCaseImpl(ObjectStoragePort objectStoragePort) {
		this.objectStoragePort = objectStoragePort;
	}

	@Override
	public String execute(String originalFilename, String contentType, byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			throw new IllegalArgumentException("File bytes cannot be empty");
		}

		String ext = "";
		if (originalFilename != null) {
			int dot = originalFilename.lastIndexOf('.');
			if (dot >= 0 && dot < originalFilename.length() - 1) {
				ext = originalFilename.substring(dot);
			}
		}

		String key = UUID.randomUUID() + ext;
		objectStoragePort.put(key, bytes, contentType);
		return key;
	}
}
