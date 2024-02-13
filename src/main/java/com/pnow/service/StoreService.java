package com.pnow.service;

import com.pnow.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository storeRepository;
}
