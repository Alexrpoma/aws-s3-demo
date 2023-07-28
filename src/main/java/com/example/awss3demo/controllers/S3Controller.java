package com.example.awss3demo.controllers;

import com.example.awss3demo.services.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/aws-s3")
@RequiredArgsConstructor
public class S3Controller {

  private final S3Service s3Service;

  @GetMapping
  public String welcome() {
    return "Welcome to AWS-S3!!";
  }

  @PostMapping("/upload")
  public String uploadFile(@RequestParam("file")MultipartFile multipartFile) throws IOException {
    return s3Service.uploadFile(multipartFile);
  }

}
