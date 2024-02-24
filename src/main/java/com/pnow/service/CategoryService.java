package com.pnow.service;

import com.pnow.domain.category.Category;
import com.pnow.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    //카테고리 전체 목록 조회
    @Transactional(readOnly = true)
    public List<Category> findCategoryList(){
        return categoryRepository.findAll();
    }
}
