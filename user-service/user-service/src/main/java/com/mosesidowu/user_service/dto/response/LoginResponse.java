package com.mosesidowu.user_service.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String responseCode;
    private String responseMessage;
    private String token;

}
