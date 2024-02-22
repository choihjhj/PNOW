package com.pnow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor  //기본생성자
@AllArgsConstructor
public class StoreDTO {
    private Long id;                    //가게 id
    private String storeName;           //가게 이름
    private String openingTime;         //오픈 시간
    private String closingTime;         //오프 시간
    private String cityName;            //도시 이름
    private String districtName;        //지역 이름
    private String detailAddress;       //상세 주소
    private String phoneNumber;         //전화 번호
}
