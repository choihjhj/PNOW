package com.pnow.controller;

import com.pnow.config.auth.LoginUser;
import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.domain.City;
import com.pnow.domain.Store;
import com.pnow.domain.category.Category;
import com.pnow.dto.StoreDTO;
import com.pnow.service.BookmarkService;
import com.pnow.service.CategoryService;
import com.pnow.service.CityService;
import com.pnow.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;
    private final CategoryService categoryService;
    private final CityService cityService;
    private final BookmarkService bookmarkService;

    /*
     * store.html 접속
     * -카테고리, 지역 조회 후 model에 저장
     * GET /stores
     * return "/stores/store"
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

        return "stores/store";
    }

    /*
     * 가게 목록 조회
     * GET /stores/list/{categoryId}/{cityId}/{districtId}
     * return List<StoreDTO>
     * */
    @GetMapping("/list/{categoryId}/{cityId}/{districtId}")
    @ResponseBody
    public List<StoreDTO> getStoreList(@PathVariable("categoryId") Long categoryId,
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
     * - 로그인했으면 로그인한유저가 해당 가게를 즐겨찾기 했는지 Bookmark model에 저장
     * GET /stores/detail/{id}
     * return "/stores/storeDetail"
     * */
    @GetMapping("/detail/{id}")
    public String getStore(Model model, @PathVariable("id") Long id, @LoginUser SessionUserDTO user ){
        log.info("/store/detail/{id} get 메소드 진입 storeId = {}",id);

        StoreDTO storeDTO = storeService.findStoreDTO(id);
        model.addAttribute("store", storeDTO);
        if(user != null){
            model.addAttribute("bookmark", bookmarkService.findBookmarkWithUserIdAndStoreId(user.getId(),id));
        }

        return "stores/storeDetail";
    }

    /*
     * storeSearch.html 접속
     * 가게 이름 조회
     * - keywork model에 저장
     * - 키워드에 해당하는 가게들 model에 저장
     * GET /stores/search?keyword=
     * return "/stores/storeSearch"
     * */
    @GetMapping("/search")
    public String searchStore(@RequestParam("keyword") String keyword, Model model){
        log.info("가게이름 검색 메소드 진입 keyword={}",keyword);
        model.addAttribute("keyword", keyword);

        List<Store> storeList =storeService.findSearchStore(keyword);
        log.info("storeList {}",storeList);
        model.addAttribute("storeList",storeList);

        return "stores/storeSearch";
    }


}
