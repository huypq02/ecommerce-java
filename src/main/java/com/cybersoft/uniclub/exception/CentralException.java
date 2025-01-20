package com.cybersoft.uniclub.exception;

import com.cybersoft.uniclub.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CentralException {
    @ExceptionHandler({InsertException.class})
    public ResponseEntity<?> centralLog(Exception e) {
        BaseResponse response = new BaseResponse();
        response.setCode(99);
        response.setMessage(e.getMessage());

        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler({FileUploadException.class})
    public ResponseEntity<?> centralLogFileUpload(Exception e) {
        BaseResponse response = new BaseResponse();
        response.setCode(01);
        response.setMessage(e.getMessage());

        return ResponseEntity.badRequest().body(response);
    }
}
