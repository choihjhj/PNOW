package com.pnow.controller;

import com.pnow.config.auth.LoginUser;
import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.domain.Category;
import com.pnow.domain.City;
import com.pnow.dto.StoreDTO;
import com.pnow.service.CategoryService;
import com.pnow.service.CityService;
import com.pnow.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;
    private final CategoryService categoryService;
    private final CityService cityService;

    /*
     * store.html 접속
     * -카테고리, 지역 조회 후 model에 저장
     * GET /store
     * return "/store/store"
     * */
    @GetMapping
    public String store(Model model) {
        log.info("/store get 메소드 진입");

        //category목록 조회 후 model에 데이터 저장
        List<Category> categoryList = categoryService.findCategoryList();
        model.addAttribute("categoryList", categoryList);

        //city목록 조회 후 model에 데이터 저장
        List<City> cityList = cityService.findCityList();
        model.addAttribute("cityList", cityList);

        return "store/store";
    }

    /*
     * 가게 목록 조회
     * GET /store/list/{categoryId}/{cityId}/{districtId}
     * return List<StoreDTO>
     * */
    @GetMapping("/list/{categoryId}/{cityId}/{districtId}")
    @ResponseBody
    public List<StoreDTO> storeListRead(@PathVariable("categoryId") Long categoryId,
                                        @PathVariable("cityId") Long cityId,
                                        @PathVariable("districtId") Long districtId) {
        log.info("/store/list/{categoryId}/{cityId}'/{districtId} get 메소드 진입. " +
                "categoryId = {}, cityId = {}, districtId = {}", categoryId, cityId, districtId);
        return storeService.findStoreDTOList(categoryId, cityId, districtId);

    }

    /*
     * storeDetail.html 접속
     * 가게 조회
     * - 메뉴,가격도 담은 storeDTO model에 저장
     * GET /store/detail/{id}
     * return "/store/storeDetail"
     * */
    @GetMapping("/detail/{id}")
    public String storeRead(Model model, @PathVariable("id") Long id, @LoginUser SessionUserDTO user ){
        log.info("/store/detail/{id} get 메소드 진입 storeId = {}",id);

        StoreDTO storeDTO = storeService.findStoreDTO(id);
        model.addAttribute("store", storeDTO);

        return "store/storeDetail";
    }




/*
    @GetMapping("/store/list/{categoryId}")
    public String storeListRead(Model model, @PathVariable("categoryId") Long categoryId, HttpSession httpSession) {
        // 전달받은 categoryId에 해당하는 store 리스트 모델에 저장
        List<Store> storeList = storeService.getStoreList(categoryId);
        model.addAttribute("storeList", storeList);

        // 세션이 존재하고 cityId 속성이 존재하는 경우에만 모델에 추가
        if (httpSession != null && httpSession.getAttribute("cityId") != null) {
            model.addAttribute("cityId", httpSession.getAttribute("cityId"));
        }

        // categoryId에 해당하는 CategoryType의 이름을 가져와서 모델에 추가, enum 활용
        CategoryType categoryType = CategoryType.values()[categoryId.intValue() - 1]; // categoryId를 이용하여 CategoryType을 가져옴, 배열은 0번지부터시작, enum은 1번지부터 시작
        model.addAttribute("categoryName", categoryType.name()); // CategoryType의 이름을 모델에 추가

        return "store/storeList";
    }

    @GetMapping ("/store/detail/{id}")
    public String storeDetail(Model model, @PathVariable("id") Long id){
        return "store/storeDetail";
    }
*/
}
