package com.pnow.domain.category;

import com.pnow.domain.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Getter
@Entity
@NoArgsConstructor //기본생성자, JUnit test에서 @Builder사용하려고
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType categoryName;


    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<Store> storeList;

    @Builder
    public Category(CategoryType categoryName) {
        this.categoryName = categoryName;
    }

}
