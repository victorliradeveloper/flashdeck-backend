package com.profitai.storage.application.usecase;

import com.profitai.storage.domain.port.ObjectStoragePort;
import com.profitai.storage.domain.valueobject.StoredObject;

public class DownloadObjectUseCaseImpl implements DownloadObjectUseCase {

	private final ObjectStoragePort objectStoragePort;

	public DownloadObjectUseCaseImpl(ObjectStoragePort objectStoragePort) {
		this.objectStoragePort = objectStoragePort;
	}

	@Override
	public StoredObject execute(String key) {
		if (key == null || key.isBlank()) {
			throw new IllegalArgumentException("Key cannot be blank");
		}
		return objectStoragePort.get(key);
	}
}
