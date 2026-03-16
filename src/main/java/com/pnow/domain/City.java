package com.pnow.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor //기본생성자, JUnit test에서 @Builder사용하려고
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long id;

    @Column(nullable = false)
    private String cityName; //도 이름

    @OneToMany(mappedBy = "city", cascade = CascadeType.REMOVE)
    private List<District> districtList; //city_id 해당하는 지역(시군구) List
    
    @Builder
    public City(String cityName) {
    	this.cityName=cityName;
    	
    }
}
