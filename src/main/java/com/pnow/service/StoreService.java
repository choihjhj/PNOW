package com.pnow.service;

import com.pnow.domain.Store;
import com.pnow.exception.DataNotFoundException;
import com.pnow.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository storeRepository;
    public List<Store> getStoreList(Long categoryId){
        List<Store> storeList = storeRepository.findByCategoryId(categoryId);
        if(storeList.isEmpty()){
            throw new DataNotFoundException("No store found for category with ID: " + categoryId);
        }
        return storeList;


    }
}
