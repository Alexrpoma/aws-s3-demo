package com.example.awss3demo.controllers;

import com.example.awss3demo.services.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/aws-3")
@RequiredArgsConstructor
public class S3Controller {

  private final S3Service s3Service;

  @PostMapping("/upload")
  public String uploadFile(@RequestParam("file")MultipartFile multipartFile) throws IOException {
    return s3Service.uploadFile(multipartFile);
  }

}
