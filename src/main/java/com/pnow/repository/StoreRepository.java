package com.pnow.repository;


import com.pnow.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    //categoryId,districtId에 해당하는 가게들 storeName 오름차순 정렬해서 가져옴
    List<Store> findByCategoryIdAndDistrictIdOrderByStoreNameAsc(Long categoryId, Long districtId); //추가로직 필요하므로 일단 엔티티에 받기(추가로직은 서비스에서 작업)

    //keyword에 해당하는 가게들 가져옴
    @Query("SELECT s FROM Store s WHERE s.storeName LIKE %:keyword%")
    List<Store> findKeyword(@Param("keyword") String keyword);
}
