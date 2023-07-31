package com.example.awss3demo.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IS3Service {
  String uploadFile(MultipartFile multipartFile) throws IOException;
  String downloadFile(String fileName) throws IOException;
}
