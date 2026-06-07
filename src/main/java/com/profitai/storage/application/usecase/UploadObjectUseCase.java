package com.profitai.storage.application.usecase;

public interface UploadObjectUseCase {
	String execute(String originalFilename, String contentType, byte[] bytes);
}
