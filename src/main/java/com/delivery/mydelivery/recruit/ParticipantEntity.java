package com.delivery.mydelivery.recruit;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "participant")
public class ParticipantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int participantId;

    private int recruitId;
    private int userId;
    private String participantType;
    private int paymentMoney;
    private int paymentStatus;

}
