package com.pnow.service;

import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.domain.user.User;
import com.pnow.dto.UserUpdateDto;
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
    public void updateUser(Long id, UserUpdateDto userUpdateDto) {
        // 회원 정보 조회
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found" + id));

        //name 업데이트
        user.setName(userUpdateDto.getName());
        userRepository.save(user);

    }

    //회원 탈퇴
    @Transactional
    public  void deleteUser(Long id){
        // 회원 정보 조회
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found" + id));

        // 회원 삭제
        userRepository.delete(user);

    }
}
