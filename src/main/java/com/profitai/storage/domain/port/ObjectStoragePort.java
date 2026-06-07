package com.profitai.storage.domain.port;

import com.profitai.storage.domain.valueobject.StoredObject;

public interface ObjectStoragePort {
	void put(String key, byte[] bytes, String contentType);
	StoredObject get(String key);
}
