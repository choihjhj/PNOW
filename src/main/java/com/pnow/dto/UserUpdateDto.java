package com.pnow.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter //UserControllerTest용
public class UserUpdateDto {
    @NotBlank(message = "이름은 필수 값입니다.")
    private String name; //이름
}
