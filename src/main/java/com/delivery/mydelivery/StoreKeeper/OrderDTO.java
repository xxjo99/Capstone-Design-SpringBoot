package com.delivery.mydelivery.StoreKeeper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDTO {

    private int recruitId;
    private String menuName;
    private String selectOption;
    private int amount;
    private int totalPrice;

}
