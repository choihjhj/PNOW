package com.pnow.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class DistrictDTO {
    private Long id;
    private String districtName;

    // 필드를 초기화하는 생성자 추가
    public DistrictDTO(Long id, String districtName) {
        this.id = id;
        this.districtName = districtName;
    }
}
