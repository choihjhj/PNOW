package com.pnow.dto;

import com.pnow.domain.category.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor  //기본생성자
@AllArgsConstructor //모든생성자
public class StoreDTO {
    private Long id;                    //가게 id
    private String storeName;           //가게 이름
    private String openingTime;         //오픈 시간
    private String closingTime;         //오프 시간
    private String cityName;            //도시 이름
    private String districtName;        //지역 이름
    private String detailAddress;       //상세 주소
    private String phoneNumber;         //전화 번호
    private CategoryType categoryName;  //카테고리 이름
    private List<MenuDTO> menuList;     //메뉴가격리스트
    private String storeStatus;         //영업상태(영업중, 영업준비중)
}
