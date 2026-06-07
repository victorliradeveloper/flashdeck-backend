package com.profitai.storage.domain.valueobject;

public record StoredObject(byte[] bytes, String contentType) {
}
