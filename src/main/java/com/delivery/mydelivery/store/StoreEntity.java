package com.delivery.mydelivery.store;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="store_info")
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int storeId;

    private String storeName;
    private String category;
    private String storePhoneNum;
    private String openTime;
    private String closeTime;

    @Override
    public String toString() {
        return "StoreEntity{" +
                "storeId=" + storeId +
                ", storeName='" + storeName + '\'' +
                ", category='" + category + '\'' +
                ", storePhoneNum='" + storePhoneNum + '\'' +
                ", openTime='" + openTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                '}';
    }
}
