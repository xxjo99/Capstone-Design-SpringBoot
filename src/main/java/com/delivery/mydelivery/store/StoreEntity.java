package com.delivery.mydelivery.store;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

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
    private Timestamp openTime;
    private Timestamp closeTime;
    private String storeImageUrl;
    private String storeAddress;
    private String deliveryAvailablePlace;
    private String deliveryTip;
    private Integer minimumDeliveryPrice;
    private String storeInfo;
    private String deliveryTime;
}
