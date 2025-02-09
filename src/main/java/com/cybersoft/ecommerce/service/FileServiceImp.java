package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.exception.FileUploadException;
import com.cybersoft.ecommerce.exception.InsertException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileServiceImp implements FileService {

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public String uploadFile(MultipartFile files) {
            try{
                Path root = Paths.get(uploadPath);
                System.out.println(root);
                if (!Files.exists(root)) {
                    Files.createDirectories(root);
                }
                Path filePath = root.resolve(Objects.requireNonNull(files.getOriginalFilename()));
                Files.copy(files.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                return filePath.getFileName().toString();
            } catch(Exception e) {
                throw new FileUploadException("Cannot upload file");
            }

    }

    @Override
    public Resource downloadFile(String fileName) {
        try {
            Path imagePath = Paths.get(uploadPath).resolve(fileName);
            Resource resource = new UrlResource(imagePath.toUri());
            if (resource.exists()) {
                return resource;
            }
            throw new InsertException("File not found");
        } catch (Exception e) {
            throw new FileUploadException("Cannot download file");
        }
    }
}
