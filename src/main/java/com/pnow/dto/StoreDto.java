package com.pnow.dto;

import com.pnow.domain.Menu;
import com.pnow.domain.Store;
import com.pnow.domain.category.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor  //기본생성자
@AllArgsConstructor //모든생성자
public class StoreDto implements Serializable {
    private Long id;                    //가게 id
    private String storeName;           //가게 이름
    private String openingTime;         //오픈 시간
    private String closingTime;         //오프 시간
    private String cityName;            //도시 이름
    private String districtName;        //지역 이름
    private String detailAddress;       //상세 주소
    private String phoneNumber;         //전화 번호
    private CategoryType categoryName;  //카테고리 이름
    private List<MenuDto> menuList;     //메뉴가격리스트
    private String storeStatus;         //영업상태(영업중, 영업준비중)

    public StoreDto(Store store) {
        this.id = store.getId();
        this.storeName = store.getStoreName();
        this.storeStatus = getStoreStatus(store);
        this.openingTime = formatTime(store.getOpeningTime());
        this.closingTime = formatTime(store.getClosingTime());
        this.cityName = store.getDistrict().getCity().getCityName();
        this.districtName = store.getDistrict().getDistrictName();
        this.detailAddress = store.getDetailAddress();
        this.phoneNumber = store.getPhoneNumber();
        this.categoryName = store.getCategory().getCategoryName();
        this.menuList = mapToMenuDTOList(store.getMenuList());
    }

    private String getStoreStatus(Store store) {
        LocalTime now = LocalTime.now();
        return (now.compareTo(store.getOpeningTime()) >= 0 && now.compareTo(store.getClosingTime()) < 0) ?
                "영업중" : "영업준비중";
    }

    private String formatTime(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    private List<MenuDto> mapToMenuDTOList(List<Menu> menuList) {
        return menuList.stream()
                .map(menu -> new MenuDto(menu.getMenuName(), menu.getPrice()))
                .collect(Collectors.toList());
    }

}
