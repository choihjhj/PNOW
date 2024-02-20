package com.pnow.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long id;

    @Column(nullable = false)
    private String cityName; //도 이름


    @OneToMany(mappedBy = "city", cascade = CascadeType.REMOVE)
    private List<District> districtList; //city_id 해당하는 시군구 List
}
