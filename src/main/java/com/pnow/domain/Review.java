//package com.pnow.domain;
//
//import com.pnow.domain.user.User;
//import lombok.Getter;
//
//import javax.persistence.*;
//import java.time.LocalDate;
//@Setter //jpa 테스트를 위해
//@Getter
//@Entity
//public class Review {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "review_id")
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "store_id")
//    private Store store;
//
//    @Column
//    private String content; //리뷰내용
//
//    @Column(nullable = false)
//    private int rating; //별점
//
//    @Column(nullable = false)
//    private LocalDate writtenDate; //작성일
//
//
//}
