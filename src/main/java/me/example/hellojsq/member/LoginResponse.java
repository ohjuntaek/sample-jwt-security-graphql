package me.example.hellojsq.member;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

@Data
public class LoginResponse {
    private String loginId;
    private String password;
    private String name;
    private String role;
    private String token;

    public LoginResponse(Member currentUser, String token) {
        this.loginId = currentUser.getLoginId();
        this.password = currentUser.getPassword();
        this.name = currentUser.getName();
        this.role = currentUser.getRole();
        this.token = token;
    }
}
