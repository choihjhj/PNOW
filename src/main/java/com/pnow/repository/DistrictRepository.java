package com.pnow.repository;

import com.pnow.domain.District;
import com.pnow.dto.DistrictDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    // 도시의 시군구 리스트를 DistrictDTO로 가져오는 쿼리
    @Query("SELECT new com.pnow.dto.DistrictDTO(d.id, d.districtName) FROM District d WHERE d.city.id = :cityId") //DTO에 바로 받아 넣기
    List<DistrictDTO> findDistrictDTOsByCityId(@Param("cityId") Long cityId);
}
