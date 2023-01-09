package com.delivery.mydelivery.store;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "store")
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int storeId;

    private String storeName;
    private String category;
    private String storePhoneNum;
    private String openTime;
    private String closeTime;
    private String storeImageUrl;
    private String storeAddress;
    private String deliveryTip;
    private Integer minimumDeliveryPrice;
}
