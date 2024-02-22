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
                    storeDTO.setOpeningTime(store.getOpeningTime());
                    storeDTO.setClosingTime(store.getClosingTime());
                    storeDTO.setCityName(cityName);
                    storeDTO.setDistrictName(districtName);
                    storeDTO.setDetailAddress(store.getDetailAddress());
                    log.info("storeDTO.getStoreName() = {}", storeDTO.getStoreName());
                    return storeDTO;
                })
                .collect(Collectors.toList());

    }
}


//    @Transactional(readOnly = true)
//    public List<StoreDTO> findStoreDTOList(Long categoryId, Long cityId, Long districtId) {
//        // 여기서는 단순히 예시로 빈 리스트를 반환하도록 함
//        return storeRepository.findStoresByCategoryCategoryNameAndDistrictCityCityNameAndDistrictDistrictName(categoryId, cityId, districtId)
//                .stream()                     /*--repository에서 받아온 컬렉션(List<Store>)을 stream()으로 변환*/
//                .map(this::convertToDTO)        /*--convertToDTO() 메소드와 매핑*/
//                .collect(Collectors.toList());  /*--스트림의 요소를 컬렉션(List)으로 수집*/
//
//    }
//
//    // Store 엔티티를 StoreDTO로 변환하는 메소드
//    private StoreDTO convertToDTO(Store store) {
//        StoreDTO storeDTO = new StoreDTO();
//        storeDTO.setId(store.getId());
//        storeDTO.setStoreName(store.getStoreName());
//        storeDTO.setOpeningTime(store.getOpeningTime());
//        storeDTO.setClosingTime(store.getClosingTime());
//        storeDTO.setCityName(store.getDistrict().getCity().getCityName());
//        storeDTO.setDistrictName(store.getDistrict().getDistrictName());
//        storeDTO.setDetailAddress(store.getDetailAddress());
//        return storeDTO;
//    }
//    public List<Store> getStoreList(Long categoryId){
//        List<Store> storeList = storeRepository.findByCategoryId(categoryId);
//        if(storeList.isEmpty()){
//            throw new DataNotFoundException("No store found for category with ID: " + categoryId);
//        }
//        return storeList;
//
//
//    }

