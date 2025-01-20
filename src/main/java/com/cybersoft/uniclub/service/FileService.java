package com.cybersoft.uniclub.service;

import com.cybersoft.uniclub.request.InsertProductionRequest;
import org.springframework.core.io.Resource;

public interface FileService {
    void uploadFile(InsertProductionRequest file);
    Resource downloadFile(String fileName);
}
