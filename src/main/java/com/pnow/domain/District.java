package com.pnow.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(nullable = false)
    private String districtName; //시군구 이름

    @OneToMany(mappedBy = "district", cascade = CascadeType.REMOVE)
    private List<Store> storeList; //district_id 해당하는 storeList
}
