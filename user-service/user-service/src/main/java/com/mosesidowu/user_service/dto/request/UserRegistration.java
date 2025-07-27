package com.mosesidowu.user_service.dto.request;

import lombok.Data;

@Data
public class UserRegistration {

    private String name;
    private String email;
    private String password;

}
