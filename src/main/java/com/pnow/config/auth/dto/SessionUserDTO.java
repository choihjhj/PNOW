package com.pnow.config.auth.dto;

import com.pnow.domain.user.User;
import lombok.Getter;
import java.io.Serializable;

@Getter
public class SessionUserDTO implements Serializable {
    Long id;
    private String name;
    private String email;
    private String picture;

    public SessionUserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}