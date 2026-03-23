package com.pnow.domain;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.pnow.domain.Reservation.Reservation;
import com.pnow.domain.category.Category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor //기본생성자, JUnit test에서 @Builder사용하려고
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    @Column(nullable = false)
    private String storeName; //음식점명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district; //시군구 district_id (도 관련 city_id도 내포되어 있음)

    @Column(nullable = false)
    private String detailAddress; //나머지 상세주소

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

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Menu> menuList;
    
    @Builder
    public Store(Long id,Category category,String storeName,District district,String detailAddress,String phoneNumber,
    		LocalTime openingTime,LocalTime closingTime) {
    	this.id=id;
    	this.category=category;
    	this.storeName=storeName;
    	this.district=district;
    	this.detailAddress=detailAddress;
    	this.phoneNumber=phoneNumber;
    	this.openingTime=openingTime;
    	this.closingTime=closingTime;
    	
    }
    @Builder
    public Store(Category category,String storeName,District district,String detailAddress,String phoneNumber,
    		LocalTime openingTime,LocalTime closingTime) {
    	this.category=category;
    	this.storeName=storeName;
    	this.district=district;
    	this.detailAddress=detailAddress;
    	this.phoneNumber=phoneNumber;
    	this.openingTime=openingTime;
    	this.closingTime=closingTime;
    	
    }
    
}
