package com.example.awss3demo.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IS3Service {
  String uploadFile(MultipartFile multipartFile) throws IOException;
  String downloadFile(String fileName) throws IOException;
  List<String> listFiles() throws IOException;
}
