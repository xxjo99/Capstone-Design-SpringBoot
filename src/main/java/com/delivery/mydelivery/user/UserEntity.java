package com.delivery.mydelivery.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity // 엔티티 클래스로 설정
@Getter // lombok getter 자동생성
@Setter // lombok setter 자동생성
/*
    db에서 엔티티클래스의 이름으로 테이블을 검색해서 가져옴
    테이블 이름을 따로 지정해야함
    테이블 이름 user로 설정
*/
@Table(name = "user")
public class UserEntity {
    @Id // 식별자 (기본키)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 식별자 생성을 db에게 맡김
    private int userId;
    private String email; // 사용자 이메일
    private String pw; // 사용자 비밀번호
    private String name; // 사용자 이름
    private String birth; // 사용자 생년월일
    private String phoneNum; // 사용자 휴대폰 번호

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", pw='" + pw + '\'' +
                ", name='" + name + '\'' +
                ", birth='" + birth + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }
}
