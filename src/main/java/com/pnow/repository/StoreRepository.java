package com.pnow.repository;


import com.pnow.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByCategoryIdAndDistrictIdOrderByStoreNameAsc(Long categoryId, Long districtId); //추가로직 필요하므로 일단 엔티티에 받기(추가로직은 서비스에서 작업)
}
