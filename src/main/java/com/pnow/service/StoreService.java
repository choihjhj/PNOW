package com.pnow.service;

import com.pnow.domain.Store;
import com.pnow.dto.StoreDTO;
import com.pnow.repository.DistrictRepository;
import com.pnow.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class StoreService {

    private final StoreRepository storeRepository;
    private final DistrictRepository districtRepository;

    @Transactional(readOnly = true)
    public List<StoreDTO> findStoreDTOList(Long categoryId, Long cityId, Long districtId) {
        // categoryId, districtId에 해당하는 Store 엔티티 가져오기
        List<Store> stores = storeRepository.findByCategoryIdAndDistrictIdOrderByStoreNameAsc(categoryId, districtId);

        // List<Store>를 List<StoreDTO>로 변환
        return stores.stream()
                .map(store -> {
                    StoreDTO storeDTO = new StoreDTO();
                    storeDTO.setId(store.getId());
                    storeDTO.setStoreName(store.getStoreName());
                    storeDTO.setOpeningTime(store.getOpeningTime().format(DateTimeFormatter.ofPattern("HH:mm"))); //String으로 포맷
                    storeDTO.setClosingTime(store.getClosingTime().format(DateTimeFormatter.ofPattern("HH:mm"))); //String으로 포맷
                    storeDTO.setCityName(store.getDistrict().getCity().getCityName());
                    storeDTO.setDistrictName(store.getDistrict().getDistrictName());
                    storeDTO.setDetailAddress(store.getDetailAddress());
                    log.info("storeDTO.getStoreName() = {}", storeDTO.getStoreName());
                    return storeDTO;
                })
                .collect(Collectors.toList());

    }

//    @Transactional(readOnly = true)
//    public StoreDTO findStoreDTO(Long id){
//        Optional<Store> store = storeRepository.findById(id);
//        if (!store.isPresent()) {
//            throw new DataNotFoundException("store not found");
//        }
//
//
//
//
//    }
}

