package com.ec7205.event_hub.event_service_api.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${AWS_ACCESS_KEY_ID:${accessKey:}}")
    private String accessKey;

    @Value("${AWS_SECRET_ACCESS_KEY:${secret:}}")
    private String secret;

    @Value("${region}")
    private String region;

    @Bean
    public AmazonS3 s3() {
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials()))
                .build();
    }

    @Bean
    public AmazonS3Client s3Client() {
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials()))
                .build();
    }

    private AWSCredentials awsCredentials() {
        if (!hasText(accessKey)) {
            throw new IllegalStateException("AWS_ACCESS_KEY_ID is missing. Set it in event-service-api/.env or the run configuration.");
        }
        if (!hasText(secret)) {
            throw new IllegalStateException("AWS_SECRET_ACCESS_KEY is missing. Set it in event-service-api/.env or the run configuration.");
        }
        return new BasicAWSCredentials(accessKey.trim(), secret.trim());
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}