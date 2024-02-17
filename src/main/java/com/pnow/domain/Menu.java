package com.pnow.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false)
    private String menuName; //메뉴명

    @Column(nullable = false)
    private int price; //메뉴가격
}
