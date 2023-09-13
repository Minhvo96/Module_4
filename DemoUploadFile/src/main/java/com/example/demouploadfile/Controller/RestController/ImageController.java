package com.example.demouploadfile.Controller.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api")
public class ImageController {
    @Autowired
    private RestTemplate restTemplate;

    private final String uploadDir = "uploads/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Lưu ảnh vào thư mục tạm thời
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            Path uploadPath = Path.of(uploadDir + fileName);
            Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

            // Gọi API bằng RestTemplate (điền URL API của bạn ở đây)
            String apiUrl = "http://localhost:8080/api/upload";

            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, fileName, String.class);

            // Xoá ảnh tạm thời sau khi gọi API
            File uploadedFile = new File(uploadPath.toString());
            uploadedFile.delete();

            return ResponseEntity.ok("Image uploaded and API called successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Image upload failed");
        }
    }
}
