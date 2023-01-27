package com.delivery.mydelivery.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity // 엔티티 클래스로 설정
@Getter // lombok getter 자동생성
@Setter
@ToString
@Table(name = "school")
public class SchoolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int schoolId;

    private String schoolName;
}
