package com.pnow.dto;

import com.pnow.domain.District;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor  //기본생성자
@AllArgsConstructor //모든생성자
public class DistrictDto implements Serializable {
    private Long id;             //지역ID
    private String districtName; //지역이름

    public static DistrictDto fromEntity(District district){
        return DistrictDto.builder()
                .id(district.getId())
                .districtName(district.getDistrictName())
                .build();

    }
}
