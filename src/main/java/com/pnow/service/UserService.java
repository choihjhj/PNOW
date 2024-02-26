package com.pnow.service;

import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.domain.user.User;
import com.pnow.dto.UserRequestDTO;
import com.pnow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    //회원 정보 조회
    @Transactional(readOnly = true)
    public User findUser(SessionUserDTO user){
        return userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found" + user.getId()));

    }
    //회원 정보 수정
    @Transactional
    public void updateUser(Long id, UserRequestDTO userRequestDTO) {
        // 회원 정보 조회
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 사용자를 찾을 수 없습니다: " + id));

        //name 업데이트
        user.setName(userRequestDTO.getName());
        userRepository.save(user);

    }

    //회원 탈퇴
    @Transactional
    public  void deleteUser(Long id){
        // 회원 정보 조회
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 사용자를 찾을 수 없습니다: " + id));

        // 회원 삭제
        userRepository.delete(user);

    }
}
