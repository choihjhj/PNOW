package com.pnow.repository;

import com.pnow.domain.category.Category;
import com.pnow.domain.category.CategoryType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest()
@Slf4j
public class CategoryRepositoryTests {
    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach //JUnit5
    public void cleanup() {
        categoryRepository.deleteAll();
    }

    @Test
    @Transactional
    public void 카테고리저장_불러오기() {
        //given
        for (CategoryType categoryType: CategoryType.values()) {
            log.info("categoryType : {}",categoryType);
            categoryRepository.save(Category.builder().categoryName(categoryType).build());
        }

        //when
        List<Category> categoryList = categoryRepository.findAll();

        //then
        Category category = categoryList.get(0);
        assertEquals(CategoryType.한식, category.getCategoryName());

    }
}
