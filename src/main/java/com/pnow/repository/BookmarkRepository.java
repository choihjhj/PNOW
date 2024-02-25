package com.pnow.repository;

import com.pnow.domain.Bookmark;
import com.pnow.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    // userId와 storeId에 해당하는 즐겨찾기 조회
    Bookmark findByUserIdAndStoreId(Long userId, Long storeId);

    //userId에 해당하는 예약 목록 조회
    List<Bookmark> findAllByUser(User user);
}
