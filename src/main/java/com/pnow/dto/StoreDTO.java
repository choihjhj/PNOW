package com.pnow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor  //기본생성자
@AllArgsConstructor //모든생성자
public class StoreDTO {
    private Long id;                    //가게 id
    private String storeName;           //가게 이름
    private LocalTime openingTime;      //오픈 시간
    private LocalTime closingTime;      //오프 시간
    private String cityName;            //도시 이름
    private String districtName;        //지역 이름
    private String detailAddress;       //상세 주소
}