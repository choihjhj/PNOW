package com.pnow.service;

import com.pnow.domain.Menu;
import com.pnow.domain.Store;
import com.pnow.dto.MenuDTO;
import com.pnow.dto.StoreDTO;
import com.pnow.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class StoreService {

    private final StoreRepository storeRepository;

    //가게 목록 조회
    @Transactional(readOnly = true)
    public List<StoreDTO> findStoreDTOList(Long categoryId, Long cityId, Long districtId) {
        List<Store> stores = storeRepository.findByCategoryIdAndDistrictIdOrderByStoreNameAsc(categoryId, districtId);
        return stores.stream()
                .map(this::mapToStoreDTO)
                .collect(Collectors.toList());
    }

    private StoreDTO mapToStoreDTO(Store store) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setId(store.getId());
        storeDTO.setStoreName(store.getStoreName());
        storeDTO.setStoreStatus(getStoreStatus(store)); //영업상태 셋팅
        storeDTO.setOpeningTime(formatTime(store.getOpeningTime())); //LocalTime -> String "HH:mm"으로 포맷
        storeDTO.setClosingTime(formatTime(store.getClosingTime())); //LocalTime -> String "HH:mm"으로 포맷
        storeDTO.setCityName(store.getDistrict().getCity().getCityName());
        storeDTO.setDistrictName(store.getDistrict().getDistrictName());
        storeDTO.setDetailAddress(store.getDetailAddress());
        log.info("storeDTO.getStoreName() = {}", storeDTO.getStoreName());
        return storeDTO;
    }

    private String getStoreStatus(Store store) {
        LocalTime now = LocalTime.now();
        return (now.compareTo(store.getOpeningTime()) >= 0 && now.compareTo(store.getClosingTime()) < 0) ?
                "영업중" : "영업준비중";
    }

    private String formatTime(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    //가게 조회
    @Transactional(readOnly = true)
    public StoreDTO findStoreDTO(Long id){
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Store not found" + id));

        return mapToDetailedStoreDTO(store);
    }

    private StoreDTO mapToDetailedStoreDTO(Store store) {
        StoreDTO storeDTO = mapToStoreDTO(store); //공통 셋팅 후 아래 나머지 추가 셋팅
        storeDTO.setPhoneNumber(store.getPhoneNumber());
        storeDTO.setCategoryName(store.getCategory().getCategoryName());
        storeDTO.setMenuList(mapToMenuDTOList(store.getMenuList())); //List<Menu>를 List<MenuDTO>로 변환
        return storeDTO;
    }

    private List<MenuDTO> mapToMenuDTOList(List<Menu> menuList) {
        return menuList.stream()
                .map(menu -> new MenuDTO(menu.getMenuName(), menu.getPrice()))
                .collect(Collectors.toList());
    }


}

