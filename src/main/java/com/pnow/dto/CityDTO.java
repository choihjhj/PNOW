package com.pnow.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@Getter
@Setter
public class CityDTO {
    private Long id;

    private List<DistrictDTO> districtList;
    // 필드를 초기화하는 생성자 추가
    public CityDTO(Long id,  List<DistrictDTO> districtList) {
        this.id = id;

        this.districtList = districtList;
    }
}
