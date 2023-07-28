package com.example.awss3demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {

  private final S3Client s3Client;

  public String uploadFile(MultipartFile multipartFile) throws IOException {
    try {
      String fileName = multipartFile.getOriginalFilename();
      PutObjectRequest putObjectRequest = PutObjectRequest.builder()
          .bucket("aws-spring-alx-bucket0") //AWS Bucket name.
          .key(fileName)
          .build();
      s3Client.putObject(putObjectRequest, RequestBody.fromBytes(multipartFile.getBytes()));
      return "The file was uploaded successfully";
    }catch (IOException ioe) {
      throw new IOException(ioe.getMessage());
    }
  }
}
