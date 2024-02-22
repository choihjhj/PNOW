package com.pnow.service;

import com.pnow.domain.Menu;
import com.pnow.domain.Store;
import com.pnow.dto.MenuDTO;
import com.pnow.dto.StoreDTO;
import com.pnow.exception.DataNotFoundException;
import com.pnow.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class StoreService {

    private final StoreRepository storeRepository;


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
        storeDTO.setOpeningTime(formatTime(store.getOpeningTime()));
        storeDTO.setClosingTime(formatTime(store.getClosingTime()));
        storeDTO.setCityName(store.getDistrict().getCity().getCityName());
        storeDTO.setDistrictName(store.getDistrict().getDistrictName());
        storeDTO.setDetailAddress(store.getDetailAddress());
        log.info("storeDTO.getStoreName() = {}", storeDTO.getStoreName());
        return storeDTO;
    }

    private String formatTime(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Transactional(readOnly = true)
    public StoreDTO findStoreDTO(Long id){
        Optional<Store> store = storeRepository.findById(id);
        if (!store.isPresent()) {
            throw new DataNotFoundException("store not found");
        }
        return mapToDetailedStoreDTO(store.get());
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

