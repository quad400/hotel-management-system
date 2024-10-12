package com.coderblack.hms.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String avatar;
    private String phoneNumber;
    private Role role;
    private boolean isAccountActivate;
    private boolean accountLocked;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
