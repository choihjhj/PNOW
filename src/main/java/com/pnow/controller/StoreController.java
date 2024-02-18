package com.pnow.controller;

import com.pnow.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class StoreController {
    private final StoreService storeService;
}
