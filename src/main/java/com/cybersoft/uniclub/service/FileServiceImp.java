package com.cybersoft.uniclub.service;

import com.cybersoft.uniclub.exception.FileUploadException;
import com.cybersoft.uniclub.exception.InsertException;
import com.cybersoft.uniclub.request.InsertProductionRequest;
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
    public void uploadFile(InsertProductionRequest file) {
        try{
            Path root = Paths.get(uploadPath);
            System.out.println(root);
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
            Path filePath = root.resolve(Objects.requireNonNull(file.getFile().getOriginalFilename()));
            Files.copy(file.getFile().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
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
