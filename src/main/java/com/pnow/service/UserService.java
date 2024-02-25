package com.pnow.service;

import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.domain.user.User;
import com.pnow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findUser(SessionUserDTO user){
        return userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found" + user.getId()));

    }
}
