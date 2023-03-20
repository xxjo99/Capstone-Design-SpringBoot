package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.orderHistory.OrderHistoryDetailEntity;
import com.delivery.mydelivery.orderHistory.OrderHistoryEntity;
import com.delivery.mydelivery.orderHistory.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderHistoryController {

    @Autowired
    private OrderHistoryService orderHistoryService;

    // 주문 내역
    @GetMapping("/order/history/{userId}")
    public List<OrderHistoryEntity> getOrderHistory(@PathVariable int userId) {
        return orderHistoryService.getOrderHistory(userId);
    }

    // 주문 내역 상세
    @GetMapping("/order/history/detail/{recruitId}/{userId}")
    public List<OrderHistoryDetailEntity> getOrderHistoryDetail(@PathVariable int recruitId, @PathVariable int userId) {
        return orderHistoryService.getOrderHistoryDetail(recruitId, userId);
    }

    // 배달 완료 이미지
    @GetMapping("/order/image/{recruitId}/{userId}")
    public byte[] getImage(@PathVariable int recruitId, @PathVariable int userId) {
        return orderHistoryService.getImage(recruitId, userId);
    }
}
