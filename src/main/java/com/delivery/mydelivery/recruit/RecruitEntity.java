package com.delivery.mydelivery.recruit;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@Table(name = "recruit")
public class RecruitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recruitId;

    private int userId;
    private String registrantPlace;
    private int storeId;
    private String place;
    private Timestamp deliveryTime;
    private int person;

}
