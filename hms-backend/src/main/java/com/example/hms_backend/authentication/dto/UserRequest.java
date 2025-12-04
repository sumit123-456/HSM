package com.example.hms_backend.authentication.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRequest {
    private Long userId;
        private String firstName;
        private String lastName;
        private String email;
    private String username;

}
