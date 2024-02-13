package com.pnow.domain.user;

import com.pnow.domain.BaseTimeEntity;
import com.pnow.domain.Bookmark;
import com.pnow.domain.Reservation;
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
@Setter //jpa 테스트를 위해
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
    private Role role; //ROLE_USER

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Bookmark> bookmarkList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Reservation> reservationList;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
//    private List<Review> reviewList;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}
//package com.pnow.domain.user;
//
//import com.pnow.domain.BaseTimeEntity;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Getter
//@NoArgsConstructor
//@Table(name = "users") //Posts와 구분하고자 넣음, 근데 user는 예약어라서 쓰면 안되니까 users라고 테이블명 셋팅
//@Entity
//public class User extends BaseTimeEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private String email;
//
//    @Column
//    private String picture;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Role role;
//
//    @Builder
//    public User(String name, String email, String picture, Role role) {
//        this.name = name;
//        this.email = email;
//        this.picture = picture;
//        this.role = role;
//    }
//
//    public User update(String name, String picture) {
//        this.name = name;
//        this.picture = picture;
//
//        return this;
//    }
//
//    public String getRoleKey() {
//        return this.role.getKey();
//    }
//
//}