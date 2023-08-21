package com.auctionapp.model.login;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfoResponseDTO {

    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private String token;
    private String type = "Bearer";

    public UserInfoResponseDTO(Long id, String username, String email, List<String> roles, String accessToken) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.token = accessToken;
    }

}
