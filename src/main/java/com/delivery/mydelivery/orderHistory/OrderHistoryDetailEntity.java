package com.delivery.mydelivery.orderHistory;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@Table(name = "orderHistoryDetail")
public class OrderHistoryDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderHistoryDetailId;

    private int recruitId;
    private int userId;
    private int storeId;
    private int menuId;
    private String selectOption;
    private int amount;
    private int totalPrice;
}
