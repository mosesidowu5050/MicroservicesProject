package com.mosesidowu.user_service.service;

import com.mosesidowu.user_service.data.model.User;
import com.mosesidowu.user_service.data.repository.UserRepository;
import com.mosesidowu.user_service.dto.request.LoginRequest;
import com.mosesidowu.user_service.dto.request.UserRegistration;
import com.mosesidowu.user_service.dto.response.LoginResponse;
import com.mosesidowu.user_service.dto.response.UserRegistrationResponse;
import com.mosesidowu.user_service.util.JwtUtil;
import com.mosesidowu.user_service.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.Utilities;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public UserRegistrationResponse register(UserRegistration userRegistration) {
        User user = userRepository.findUserByEmail(userRegistration.getEmail());
        if (user != null) {
            return UserRegistrationResponse.builder()
                    .responseCode(UserUtils.USER_ALREADY_EXISTS_ERROR_CODE)
                    .responseMessage(UserUtils.USER_ALREADY_EXISTS_ERROR_MESSAGE)
                    .build();

        }
        User newUser = User.builder()
                .email(userRegistration.getEmail())
                .password(userRegistration.getPassword())
                .name(userRegistration.getName())
                .build();

        User savedUser = userRepository.save(newUser);
        return UserRegistrationResponse.builder()
                .responseCode(UserUtils.USER_REGISTRATION_SUCCESS_CODE)
                .responseMessage(UserUtils.USER_REGISTRATION_SUCCESS_MESSAGE)
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findUserByEmail(loginRequest.getEmail());

        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            String token = jwtUtil.generateToken(user.getId(), user.getEmail());
            return LoginResponse.builder()
                .responseCode(UserUtils.LOGIN_SUCCESS_CODE)
                .responseMessage(UserUtils.LOGIN_SUCCESS_MESSAGE)
                .token(token)
                .build();
    }
        return LoginResponse.builder()
                .responseCode(UserUtils.LOGIN_FAILURE_CODE)
                .responseMessage(UserUtils.LOGIN_FAILURE_MESSAGE)
                .build();
    }
}
