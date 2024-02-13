package com.pnow.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@Setter //jpa 테스트를 위해
@Getter
@Entity
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

}
