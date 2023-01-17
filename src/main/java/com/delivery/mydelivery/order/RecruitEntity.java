package com.delivery.mydelivery.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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
    private int storeId;
    private String place;
    private String deliveryTime;
    private int person;

}
