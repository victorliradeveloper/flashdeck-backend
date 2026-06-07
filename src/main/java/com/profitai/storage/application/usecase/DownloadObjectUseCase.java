package com.profitai.storage.application.usecase;

import com.profitai.storage.domain.valueobject.StoredObject;

public interface DownloadObjectUseCase {
	StoredObject execute(String key);
}
