package org.example.srg3springminiproject.controller;


import lombok.AllArgsConstructor;

import org.example.srg3springminiproject.model.AppFile;

import org.example.srg3springminiproject.model.response.APIResponse;
import org.example.srg3springminiproject.service.AppFileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/files")
@AllArgsConstructor

public class AppFileController {
    private final AppFileService appFileService;

    @PostMapping(value ="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) throws IOException {
        String fileName = appFileService.uploadFile(file);
        APIResponse<AppFile> response = APIResponse.<AppFile>builder()
                .message("Profile upload successful.")
                .payload(new AppFile(
                        fileName,
                        file.getContentType(),
                        file.getSize()
                ))
                .status(HttpStatus.CREATED)
                .creationDate(new Date())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<?> getFileByFileName(@RequestParam String fileName) throws IOException {
        Resource resource = appFileService.getFileByFileName(fileName);
        //we need header to show profile_image
        MediaType mediaType;
        if(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif")){
            mediaType = MediaType.IMAGE_PNG;}
        else {mediaType = MediaType.APPLICATION_OCTET_STREAM;}
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=\"" + fileName + "\"")
                .contentType(mediaType)
                .body(resource);
    }

}
