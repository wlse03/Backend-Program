package me.jhj.springdeveloper.dao;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter //lombok get메서드
@NoArgsConstructor(access = AccessLevel.PROTECTED)//기본 생성자
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id; //PK

    @Column(nullable = false)
    private String title; //NOT NULL

    @Column(nullable = false)
    private String content; //NOT NULL

    @Builder //빌더라는 패턴을 사용하여 가독성을 높인다
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

