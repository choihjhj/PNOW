package com.pnow.service;

import com.pnow.dto.CityDTO;
import com.pnow.exception.DataNotFoundException;
import com.pnow.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DistrictService {
    private final DistrictRepository districtRepository;

    @Transactional(readOnly = true)
    public CityDTO findCityWithDistricts(Long cityId) {
        // CityDTO를 구성할 데이터 가져오기
        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(cityId);

        // 해당 도시의 시군구 리스트 가져오기
        cityDTO.setDistrictList(districtRepository.findDistrictDTOsByCityId(cityId));

        // 만약 시군구 리스트가 비어 있다면 예외 던지기
        if (cityDTO.getDistrictList().isEmpty()) {
            throw new DataNotFoundException("No districts found for city with ID: " + cityId);
        }

        return cityDTO;
    }
}
