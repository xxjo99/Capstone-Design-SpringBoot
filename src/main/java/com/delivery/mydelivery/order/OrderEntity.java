package com.delivery.mydelivery.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    private int userId;
    private int storeId;
    private int menuId;
    private String selectOption;
    private int amount;
    private int totalPrice;

}
