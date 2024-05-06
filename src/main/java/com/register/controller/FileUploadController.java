package com.register.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileUploadController {
    private static final String UPLOADED_FOLDER = "src/main/resources/static/";

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please select a file to upload";
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            return UPLOADED_FOLDER + file.getOriginalFilename();
        } catch (Exception e) {
            return "Failed to upload file";
        }
    }

    @GetMapping("/list")
    public String listUploadedFiles() {
        StringBuilder files = new StringBuilder();
        try {
            Files.list(Paths.get(UPLOADED_FOLDER)).forEach(file -> {
                files.append(file.getFileName()).append(" ");
            });
        } catch (Exception e) {
            return "Failed to list files: " + e.getMessage();
        }
        return files.toString();
    }
}