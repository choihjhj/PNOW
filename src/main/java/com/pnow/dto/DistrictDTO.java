package com.pnow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor  //기본생성자
@AllArgsConstructor //모든생성자
public class DistrictDTO {
    private Long id;             //지역ID
    private String districtName; //지역이름
}
