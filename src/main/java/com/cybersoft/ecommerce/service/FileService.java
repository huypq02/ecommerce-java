package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.request.InsertProductionRequest;
import org.springframework.core.io.Resource;

public interface FileService {
    void uploadFile(InsertProductionRequest file);
    Resource downloadFile(String fileName);
}
