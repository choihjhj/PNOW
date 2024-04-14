package com.pnow.service;

import com.pnow.domain.Store;
import com.pnow.dto.StoreDto;
import com.pnow.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class StoreService {

    private final StoreRepository storeRepository;

    //가게 목록 조회
    @Transactional(readOnly = true)
    public List<StoreDto> findStoreDTOList(Long categoryId, Long districtId) {
        List<Store> stores = storeRepository.findByCategoryIdAndDistrictIdOrderByStoreNameAsc(categoryId, districtId);
        return stores.stream()
                .map(StoreDto::new)
                .collect(Collectors.toList());
    }

    //가게 세부 정보 조회
    @Transactional(readOnly = true)
    public StoreDto findStoreDTO(Long id){
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StoreId not found : " + id));

        return new StoreDto(store);
    }

    //가게 이름 검색
    @Transactional(readOnly = true)
    public  List<Store> findSearchStore(String keyword){
        return storeRepository.findKeyword(keyword);

    }


}
