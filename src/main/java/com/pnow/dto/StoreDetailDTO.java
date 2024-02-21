package com.pnow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor  //기본생성자
@AllArgsConstructor //모든생성자
public class StoreDetailDTO {
    private Long id;                    //가게 id
    private String categoryName;        //카테고리 이름
    private String storeName;           //가게 이름
    private String phoneNumber;         //전화 번호
    private LocalTime openingTime;      //오픈 시간
    private LocalTime closingTime;      //오프 시간
    private String cityName;            //도시 이름
    private String districtName;        //지역 이름
    private String detailAddress;       //상세 주소
    private List<MenuDTO> menuDTOList;  //메뉴가격DTO List
}
