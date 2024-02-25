package com.pnow.repository;

import com.pnow.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    // userId와 storeId에 해당하는 즐겨찾기 조회
    Bookmark findByUserIdAndStoreId(Long userId, Long storeId);
}
