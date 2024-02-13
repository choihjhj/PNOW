package com.pnow.service;

import com.pnow.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MenuService {
    private final MenuRepository menuRepository;
}
