package com.pnow.service;

import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.domain.Bookmark;
import com.pnow.domain.Store;
import com.pnow.domain.user.User;
import com.pnow.repository.BookmarkRepository;
import com.pnow.repository.StoreRepository;
import com.pnow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    //userId, storeId 해당하는 즐겨찾기 조회 (StoreController @GetMapping("/stores/detail/{id}")에서 회원의 북마크정보 조회함)
    @Transactional(readOnly = true)
    public Bookmark findBookmarkWithUserIdAndStoreId(Long userId, Long storeId) {
        User user = findByIdOrThrow(userRepository, userId, "UserId");
        Store store = findByIdOrThrow(storeRepository, storeId, "StoreId");
        return bookmarkRepository.findByUserIdAndStoreId(user.getId(), store.getId());
    }

    //즐겨찾기 삭제
    @Transactional
    public void cancelBookmark(Long bookmarkId) {
        Bookmark bookmark = findByIdOrThrow(bookmarkRepository, bookmarkId, "BookmarkId");
        bookmarkRepository.delete(bookmark);
    }

    //즐겨찾기 등록
    @Transactional
    public void makeBookmark(Long storeId, SessionUserDTO userDTO){
        Store store = findByIdOrThrow(storeRepository, storeId, "StoreId");
        User user = findByIdOrThrow(userRepository, userDTO.getId(), "UserId");
        bookmarkRepository.save(Bookmark.builder()
                .user(user)
                .store(store)
                .build());
    }

    //userId에 해당하는 즐겨찾기 목록 조회
    @Transactional(readOnly = true)
    public List<Bookmark> findBookmarkWithUserId(SessionUserDTO userDTO){
        User user = findByIdOrThrow(userRepository, userDTO.getId(), "UserId");
        return bookmarkRepository.findAllByUser(user);

    }

    private <T> T findByIdOrThrow(JpaRepository<T, Long> repository, Long id, String entityName) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityName + " not found : " + id));
    }

}
