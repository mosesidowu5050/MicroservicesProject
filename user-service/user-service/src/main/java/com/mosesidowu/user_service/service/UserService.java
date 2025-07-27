package com.mosesidowu.user_service.service;

import com.mosesidowu.user_service.dto.request.LoginRequest;
import com.mosesidowu.user_service.dto.request.UserRegistration;
import com.mosesidowu.user_service.dto.response.LoginResponse;
import com.mosesidowu.user_service.dto.response.UserRegistrationResponse;

public interface UserService {

    UserRegistrationResponse register(UserRegistration userRegistration);

    LoginResponse login(LoginRequest loginRequest);
}
