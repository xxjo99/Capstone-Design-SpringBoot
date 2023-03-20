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
@Table(name = "orderHistory")
public class OrderHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderHistoryId;

    private int recruitId;
    private int userId;
    private int storeId;
    private int paymentMoney;
    private int participantCount;
    private byte[] deliveryCompletePicture;
    private Timestamp deliveryDate;
}
