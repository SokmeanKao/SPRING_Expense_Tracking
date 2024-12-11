package org.example.srg3springminiproject.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AppFileService {
    String uploadFile(MultipartFile file) throws IOException;
    Resource getFileByFileName(String fileName) throws IOException;
}
