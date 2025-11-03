package com.pnow.domain.user;

import com.pnow.domain.BaseTimeEntity;
import com.pnow.domain.Bookmark;
import com.pnow.domain.Reservation.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor //기본생성자
@Table(name = "users") //user는 예약어라서 쓰면 안되니까 users라고 테이블명 셋팅
@Entity
@Getter
@Setter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; //

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Bookmark> bookmarkList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Reservation> reservationList;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    //구글 로그인 개인정보 변경감지시 업데이트
    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    //PNOW 회원정보수정 기능, 메서드 오버로딩으로 update 분리
    public User update(String name) {
        this.name = name;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}