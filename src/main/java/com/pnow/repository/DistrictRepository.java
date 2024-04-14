package com.pnow.repository;

import com.pnow.domain.District;
import com.pnow.dto.DistrictDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    // cityId에 해당하는 district들을 DistrictDTO로 바로 넣기 위해  @Query 사용
    @Query("SELECT new com.pnow.dto.DistrictDto(d.id, d.districtName) FROM District d WHERE d.city.id = :cityId") //추가로직 필요없으므로 DTO에 바로 받아 넣기
    List<DistrictDto> findDistrictDTOsByCityId(@Param("cityId") Long cityId);
}
