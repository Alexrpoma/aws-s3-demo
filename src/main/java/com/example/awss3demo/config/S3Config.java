package com.example.awss3demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

  @Value("${aws.accessKeyId}")
  private String accessKeyId;

  @Value("${aws.secretKeyId}")
  private String secretKeyId;

  @Bean
  public S3Client s3Client() {
    Region region = Region.US_EAST_2; //AWS Bucket region
    AwsCredentials awsCredentials = AwsBasicCredentials.create(accessKeyId, secretKeyId);
    return S3Client.builder()
        .region(region)
        .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
        .build();
  }
}
