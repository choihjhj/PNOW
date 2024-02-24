package com.pnow.service;

import com.pnow.domain.City;
import com.pnow.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CityService {
    private final CityRepository cityRepository;
    
    //도시 전체 목록 조회
    @Transactional(readOnly = true)
    public List<City> findCityList(){
        return cityRepository.findAll();
    }
}
