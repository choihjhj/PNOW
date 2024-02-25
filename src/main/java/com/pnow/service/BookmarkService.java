package com.pnow.service;

import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.domain.Bookmark;
import com.pnow.domain.Store;
import com.pnow.domain.user.User;
import com.pnow.repository.BookmarkRepository;
import com.pnow.repository.StoreRepository;
import com.pnow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    //userId, storeId 해당하는 즐겨찾기 조회
    @Transactional(readOnly = true)
    public Bookmark findBookmarkWithUserIdAndStoreId(Long userId, Long storeId){
        return bookmarkRepository.findByUserIdAndStoreId(userId, storeId);

    }

    //즐겨찾기 삭제
    @Transactional
    public void cancelBookmark(Long storeId, Long userId){
        Bookmark bookmark = bookmarkRepository.findByUserIdAndStoreId(userId, storeId);
        if (bookmark != null) {
            bookmarkRepository.delete(bookmark);
        }

    }

    //즐겨찾기 등록
    @Transactional
    public void makeBookmark(Long storeId, SessionUserDTO userDTO){
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("Store not found" + storeId));
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found" + userDTO.getId()));

        //Bookmark 엔티티 생성
        Bookmark bookmark =new Bookmark();
        bookmark.setUser(user);
        bookmark.setStore(store);

        //즐겨찾기 저장
        bookmarkRepository.save(bookmark);
    }


}
