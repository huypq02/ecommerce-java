package com.cybersoft.ecommerce.exception;

import lombok.Data;

@Data
public class InsertException extends RuntimeException {

    private String message;

    public InsertException(String message) {
        super(message);
        this.message = message;
    }
}
