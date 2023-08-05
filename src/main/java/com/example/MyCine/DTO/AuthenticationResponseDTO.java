package com.example.MyCine.DTO;

import com.example.MyCine.Constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDTO {
    private String jwtToken;
    private String userID;
    private String email;
    private String fullName;
    private boolean isLocked;
    private Role userRoles;
}
