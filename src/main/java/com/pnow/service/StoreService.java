package com.pnow.service;

import com.pnow.domain.City;
import com.pnow.domain.District;
import com.pnow.domain.Store;
import com.pnow.dto.StoreDTO;
import com.pnow.repository.CityRepository;
import com.pnow.repository.DistrictRepository;
import com.pnow.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class StoreService {

    private final StoreRepository storeRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;

    @Transactional(readOnly = true)
    public List<StoreDTO> findStoreDTOList(Long categoryId, Long cityId, Long districtId) {
        // 1. categoryId, districtId에 해당하는 Store 엔티티 가져오기
        List<Store> stores = storeRepository.findByCategoryIdAndDistrictIdOrderByStoreNameAsc(categoryId, districtId);

        // 2. 전달받은 cityId에 해당하는 cityName 가져오기
        Optional<City> cityOptional = cityRepository.findById(cityId);
        String cityName = cityOptional.map(City::getCityName).orElse(null);

        // 3. 전달받은 districtId에 해당하는 districtName 가져오기
        Optional<District> districtOptional = districtRepository.findById(districtId);
        String districtName = districtOptional.map(District::getDistrictName).orElse(null);

        // 4. List<Store>를 List<StoreDTO>로 변환
        return stores.stream()
                .map(store -> {
                    StoreDTO storeDTO = new StoreDTO();
                    storeDTO.setId(store.getId());
                    storeDTO.setStoreName(store.getStoreName());
                    storeDTO.setOpeningTime(store.getOpeningTime().format(DateTimeFormatter.ofPattern("HH:mm"))); //String으로 포맷
                    storeDTO.setClosingTime(store.getClosingTime().format(DateTimeFormatter.ofPattern("HH:mm"))); //String으로 포맷
                    storeDTO.setCityName(cityName);
                    storeDTO.setDistrictName(districtName);
                    storeDTO.setDetailAddress(store.getDetailAddress());
                    storeDTO.setPhoneNumber(store.getPhoneNumber());
                    log.info("storeDTO.getStoreName() = {}", storeDTO.getStoreName());
                    return storeDTO;
                })
                .collect(Collectors.toList());

    }
}

