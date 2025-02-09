package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.request.InsertProductionRequest;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile file);
    Resource downloadFile(String fileName);
}
