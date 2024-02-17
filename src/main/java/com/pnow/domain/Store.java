package com.pnow.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Getter
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @Column(nullable = false)
    private String storeName; //음식점명

    @Column
    private String address; //음식점 주소

    @Column
    private String phoneNumber; //음식점 전화번호

    @Column(nullable = false)
    private LocalTime openingTime; // 오픈시간

    @Column(nullable = false)
    private LocalTime closingTime; // 오프시간

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Bookmark> bookmarkList;

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Reservation> reservationList;

//    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE)
//    private List<Review> reviewList;

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Menu> menuList;




}
