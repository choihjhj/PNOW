package com.pnow.service;

import com.pnow.dto.DistrictDTO;
import com.pnow.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DistrictService {
    private final DistrictRepository districtRepository;

    //지역 목록 조회
    @Transactional(readOnly = true)
    public List<DistrictDTO> findDistrictsWithCityId(Long cityId) {
        List<DistrictDTO> districtList = districtRepository.findDistrictDTOsByCityId(cityId);
        if (districtList.isEmpty()) {
            throw new EntityNotFoundException("CityId not found : " + cityId);
        }
        return districtList;
    }

}
