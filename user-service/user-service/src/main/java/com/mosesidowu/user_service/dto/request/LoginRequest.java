package com.mosesidowu.user_service.dto.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;

}
