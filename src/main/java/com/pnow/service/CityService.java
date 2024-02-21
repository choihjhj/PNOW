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

    @Transactional(readOnly = true)
    public List<City> findCityList(){
        return cityRepository.findAll();
    }
}
