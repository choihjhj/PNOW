package com.pnow.repository;

import com.pnow.domain.Bookmark;
import com.pnow.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
	// userId와 storeId에 해당하는 즐겨찾기 조회
	Bookmark findByUserIdAndStoreId(Long userId, Long storeId);
	
	// 사용자의 즐겨찾기 목록 조회
    // Store + District + City를 함께 조회하여 N+1 방지
	@Query("SELECT b " +
			"FROM Bookmark b " +
			"JOIN FETCH b.store s " +
			"JOIN FETCH s.district d " +
			"JOIN FETCH d.city c " +
			"WHERE b.user.id = :userId")
	List<Bookmark> findAllByUserIdWithStoreInfo(@Param("userId") Long userId);

	//userId에 해당하는 예약 목록 조회
	List<Bookmark> findAllByUser(User user);

}
