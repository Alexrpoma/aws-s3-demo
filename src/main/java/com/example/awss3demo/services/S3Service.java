package com.example.awss3demo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service implements IS3Service{

  private final S3Client s3Client;

  @Value("${download.file.path}")
  private String downloadFilePath;

  @Override
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

  @Override
  public String downloadFile(String fileName) throws IOException {
    if (!isExistFile(fileName)) {
      log.info("The file %s doesn't exist!".formatted(fileName));
      return "The file %s doesn't exist!".formatted(fileName);
    }
    GetObjectRequest objectRequest = GetObjectRequest.builder()
        .bucket("aws-spring-alx-bucket0")
        .key(fileName)
        .build();
    ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(objectRequest);

    try(FileOutputStream fileOutputStream = new FileOutputStream(downloadFilePath + fileName)) {
      byte[] read_buffer = new byte[1024];
      int read_length = 0;
      while((read_length = responseInputStream.read(read_buffer)) > 0) {
        fileOutputStream.write(read_buffer, 0, read_length);
      }
    } catch (IOException ioException) {
      log.error(ioException.getMessage());
    }
    return "File %s downloaded successfully!".formatted(fileName);
  }

  @Override
  public List<String> listFiles() throws IOException {
    try {
      ListObjectsRequest listObjects = ListObjectsRequest.builder()
          .bucket("aws-spring-alx-bucket0")
          .build();
      List<S3Object> objects = s3Client.listObjects(listObjects).contents();
      List<String> fileNames = new ArrayList<>();
      for(S3Object s3Object : objects) {
        fileNames.add(s3Object.key());
      }
      return fileNames;
    } catch (S3Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  private boolean isExistFile(String objKey) {
    try {
      HeadObjectRequest objectRequest = HeadObjectRequest.builder()
          .bucket("aws-spring-alx-bucket0")
          .key(objKey)
          .build();
      s3Client.headObject(objectRequest);
      return true;
    } catch (S3Exception s3Exception) {
      log.error(s3Exception.getMessage());
      if(s3Exception.statusCode() == 404) {
        return false;
      }
    }
    return false;
  }
}
