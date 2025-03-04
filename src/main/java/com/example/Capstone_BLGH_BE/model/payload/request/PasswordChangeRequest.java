package com.example.Capstone_BLGH_BE.model.payload.request;

import lombok.Data;

@Data
public class PasswordChangeRequest {
    private String oldPassword;
    private String newPassword;
}