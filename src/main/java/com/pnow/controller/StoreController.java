package com.pnow.controller;

import com.pnow.domain.CategoryType;
import com.pnow.domain.Store;
import com.pnow.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/store/{categoryId}")
    public String store(Model model,@PathVariable("categoryId") Long categoryId){
        //전달받은 categoryId에 해당하는 store 리스트 모델에 저장
        List<Store> storeList = storeService.getStoreList(categoryId);
        model.addAttribute("storeList",storeList);

        // categoryId에 해당하는 CategoryType의 이름을 가져와서 모델에 추가, enum 활용
        CategoryType categoryType = CategoryType.values()[categoryId.intValue()-1]; // categoryId를 이용하여 CategoryType을 가져옴, 배열은 0번지부터시작, enum은 1번지부터 시작
        model.addAttribute("categoryName", categoryType.name()); // CategoryType의 이름을 모델에 추가

        return "store/storeList";
    }

}
