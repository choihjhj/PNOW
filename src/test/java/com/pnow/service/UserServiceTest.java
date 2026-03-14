package com.pnow.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.pnow.config.auth.dto.CustomUserPrincipal;
import com.pnow.domain.user.User;
import com.pnow.dto.UserUpdateDto;
import com.pnow.repository.UserRepository;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@Mock
    UserRepository userRepository; //Mokito가 가짜 레포지토리객체 만듦

	@InjectMocks
    UserService userService; //가짜Mock 레포지토리객체를 서비스에 주입해서 서비스객체생성
	

	@Test
	@DisplayName("회원 조회 실패")
	void 회원_조회_실패() {
		System.out.println("회원_조회_실패 Test");

		// given
        Long userId = 1L;

        CustomUserPrincipal principal = mock(CustomUserPrincipal.class);
        when(principal.getId()).thenReturn(userId);

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.findUser(principal))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("UserId not found");

	}
	
	@Test
	@DisplayName("회원 조회 성공")
	void 회원_조회_성공() {

	    // given
	    Long userId = 3L;

	    CustomUserPrincipal principal = mock(CustomUserPrincipal.class);
	    when(principal.getId()).thenReturn(userId);

	    User user = new User();
	    user.setId(userId);

	    when(userRepository.findById(userId))
	            .thenReturn(Optional.of(user));

	    // when
	    User result = userService.findUser(principal);

	    // then
	    assertEquals(userId, result.getId());
	}

	@Test
	@DisplayName("회원 정보 수정")
	void 회원_정보_수정() {
		// given
        Long userId = 3L;

        User user = mock(User.class);

        UserUpdateDto dto = mock(UserUpdateDto.class);
        when(dto.getName()).thenReturn("newName");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(user.update("newName")).thenReturn(user);

        // when
        User result = userService.updateUser(userId, dto);

        // then
        assertThat(result).isEqualTo(user);
        verify(userRepository).findById(userId);
        verify(user).update("newName");
		
	}
	
	@Test
    @DisplayName("회원 탈퇴 성공")
	void 회원_탈퇴_성공() {
		// given
        Long userId = 3L;

        User user = mock(User.class);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        // when
        userService.deleteUser(userId);

        // then
        verify(userRepository).delete(user);
	}
	
}
