package com.cybersoft.uniclub.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @Email(message = "Email is invalid")
        String email,
        @NotBlank(message = "Password is required")
        String password,
        String fullname) {
}
