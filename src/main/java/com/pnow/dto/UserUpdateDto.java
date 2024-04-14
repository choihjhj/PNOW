package com.pnow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor  //기본생성자
@AllArgsConstructor //모든생성자
public class UserUpdateDto {
    @NotBlank(message = "이름은 필수 값입니다.")
    private String name; //이름
}
