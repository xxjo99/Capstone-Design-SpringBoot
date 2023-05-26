package com.delivery.mydelivery.storeKeeper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeliveryInfoDTO {

    private int recruitId;
    private String storeName;
    private int menuAmount;
    private String school;
    private String deliveryPlace;

}
