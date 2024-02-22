package com.pnow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor  //기본생성자
@AllArgsConstructor //모든생성자
public class MenuDTO {
//    private Long id;            //메뉴ID
    private String menuName;    //메뉴이름
    private int price;          //메뉴가격

}
