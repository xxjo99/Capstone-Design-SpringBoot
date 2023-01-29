package com.delivery.mydelivery.recruit;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "participantOrder")
public class ParticipantOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int participantOrderId;

    private int recruitId;
    private int participantId;
    private int storeId;
    private int menuId;
    private String selectOption;
    private int amount;
    private int totalPrice;

}
