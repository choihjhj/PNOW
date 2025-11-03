package com.pnow.config.auth.dto;

import com.pnow.domain.user.User;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class SessionUserDTO implements Serializable {
    private static final long serialVersionUID = 1L; //고정 UID, 역직렬화 오류 방지

    private Long id;
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