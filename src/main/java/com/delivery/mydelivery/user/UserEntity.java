package com.delivery.mydelivery.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity // 엔티티 클래스로 설정
@Getter // lombok getter 자동생성
@Setter
@ToString
@Table(name = "user")
public class UserEntity {
    @Id // 식별자 (기본키)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 식별자 생성을 db에게 맡김
    private int userId;

    private String email; // 사용자 이메일
    private String pw; // 사용자 비밀번호
    private String name; // 사용자 이름
    private String phoneNum; // 사용자 휴대폰 번호
    private String school; // 학교
    private int point; // 포인트
}
