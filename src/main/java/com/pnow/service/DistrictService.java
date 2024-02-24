package com.pnow.service;

import com.pnow.dto.DistrictDTO;
import com.pnow.exception.DataNotFoundException;
import com.pnow.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DistrictService {
    private final DistrictRepository districtRepository;

    //지역 목록 조회
    @Transactional(readOnly = true)
    public List<DistrictDTO> findDistrictsWithCityId(Long cityId) {

        List<DistrictDTO> districtDTOList = districtRepository.findDistrictDTOsByCityId(cityId);

        // 만약 지역 리스트가 비어 있다면 예외 던지기
        if (districtDTOList.isEmpty()) {
            throw new DataNotFoundException("No districts found for city with ID: " + cityId);
        }
        return districtDTOList;
    }
}
