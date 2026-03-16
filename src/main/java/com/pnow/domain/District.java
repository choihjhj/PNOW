package com.pnow.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor //기본생성자, JUnit test에서 @Builder사용하려고
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(nullable = false)
    private String districtName; //지역(시군구) 이름

    @OneToMany(mappedBy = "district", cascade = CascadeType.REMOVE)
    private List<Store> storeList; //district_id 해당하는 storeList
    
    @Builder
    public District(City city,String districtName) {
    	this.city=city;
    	this.districtName=districtName;
    }
}
