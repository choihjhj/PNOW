package com.pnow.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;

@Getter
@Setter
public class StoreDTO {
    private Long id;                //가게 id
    private String categoryName;    //카테고리 이름
    private String storeName;       //가게 이름
    private String phoneNumber;     //전화 번호
    private LocalTime openingTime;  //오픈 시간
    private LocalTime closingTime;  //오프 시간
    private String cityName;        //도 이름
    private String districtName;    //시군구 이름
    private String detailAddress;   //상세 주소
}
