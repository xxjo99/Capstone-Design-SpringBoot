package com.delivery.mydelivery.point;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@Table(name = "pointHistory")
public class PointHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pointId;

    private int userId;
    private int point;
    private String type;
    private int balance;
    private Timestamp dateTime;
}
