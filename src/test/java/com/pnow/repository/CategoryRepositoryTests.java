package com.pnow.repository;

import com.pnow.domain.Category;
import com.pnow.domain.CategoryType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
//@ExtendWith(SpringExtension.class) //JUnit5 버전
@SpringBootTest //자동으로 h2 데이터베이스 실행함
public class CategoryRepositoryTests {
    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach //JUnit5
    public void cleanup() {
        categoryRepository.deleteAll();
    }

    @Test
    public void 카테고리저장_불러오기() {
        //given
        for (CategoryType category: CategoryType.values()) {
            log.info("category : {}",category);
            categoryRepository.save(Category.builder().categoryName(category).build());
        }

        //when
        List<Category> categoryList = categoryRepository.findAll();

        //then
        Category category = categoryList.get(0);
        System.out.println("카테고리 이름 : " + category.getCategoryName());
        assertEquals(CategoryType.한식, category.getCategoryName());

    }


}


