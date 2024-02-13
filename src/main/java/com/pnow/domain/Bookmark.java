package com.pnow.domain;

import com.pnow.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Setter //jpa 테스트를 위해
@Getter
@Entity
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}
