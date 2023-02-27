package com.delivery.mydelivery.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@Table(name = "participationRestriction")
public class ParticipationRestrictionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int participationRestrictionId;

    private int userId;
    private Timestamp restrictionPeriod;
}
