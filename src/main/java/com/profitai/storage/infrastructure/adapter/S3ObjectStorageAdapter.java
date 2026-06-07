package com.profitai.storage.infrastructure.adapter;

import com.profitai.storage.domain.port.ObjectStoragePort;
import com.profitai.storage.domain.valueobject.StoredObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
public class S3ObjectStorageAdapter implements ObjectStoragePort {

	private final S3Client s3Client;
	private final String bucketName;

	public S3ObjectStorageAdapter(S3Client s3Client, @Value("${aws.bucket.name}") String bucketName) {
		this.s3Client = s3Client;
		this.bucketName = bucketName;
	}

	@Override
	public void put(String key, byte[] bytes, String contentType) {
		s3Client.putObject(PutObjectRequest.builder().bucket(bucketName).key(key).contentType(contentType).build(),
				RequestBody.fromBytes(bytes));
	}

	@Override
	public StoredObject get(String key) {
		ResponseBytes<GetObjectResponse> objectAsBytes = s3Client
				.getObjectAsBytes(GetObjectRequest.builder().bucket(bucketName).key(key).build());
		return new StoredObject(objectAsBytes.asByteArray(), objectAsBytes.response().contentType());
	}
}
