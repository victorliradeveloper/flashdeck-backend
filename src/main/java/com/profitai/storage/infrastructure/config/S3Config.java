package com.profitai.storage.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

	@Value("${cloud.aws.credentials.access-key}")
	private String accessKey;

	@Value("${cloud.aws.credentials.secret-key}")
	private String secretKey;

	@Value("${cloud.aws.region.static}")
	private String region;

	@Bean
	public S3Client s3Client() {
		var builder = S3Client.builder().region(Region.of(region));

		// NÃ£o derruba o startup quando as credenciais nÃ£o estiverem configuradas.
		// Em ambientes com IAM Role / credenciais do host, o DefaultCredentialsProvider
		// resolve.
		if (accessKey != null && !accessKey.isBlank() && secretKey != null && !secretKey.isBlank()) {
			AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey, secretKey);
			builder.credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials));
		} else {
			builder.credentialsProvider(DefaultCredentialsProvider.create());
		}

		return builder.build();
	}

}
