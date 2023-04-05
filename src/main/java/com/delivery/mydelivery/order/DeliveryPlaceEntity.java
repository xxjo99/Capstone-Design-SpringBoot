package com.delivery.mydelivery.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "deliveryPlace")
public class DeliveryPlaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deliveryPlaceId;

    private String place;

}
