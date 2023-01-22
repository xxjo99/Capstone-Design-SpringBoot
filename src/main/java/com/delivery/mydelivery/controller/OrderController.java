package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.order.OrderEntity;
import com.delivery.mydelivery.order.OrderService;
import com.delivery.mydelivery.recruit.RecruitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/save")
    public OrderEntity addMenu(@RequestBody OrderEntity order) {
        return orderService.addMenu(order);
    }

    @GetMapping("/order/findStore/{userId}/{storeId}")
    public List<OrderEntity> findStoreInCart(@PathVariable int userId, @PathVariable int storeId) {
        return orderService.findStore(userId, storeId);
    }

    @GetMapping("/order/getOrderList/{userId}")
    public List<OrderEntity> getOrderList(@PathVariable int userId) {
        return orderService.getOrderList(userId);
    }

    @GetMapping("/order/contentNameList/{contentIdList}")
    public List<String> getContentNameList(@PathVariable String contentIdList) {
        return orderService.getOptionContentList(contentIdList);
    }

    @PostMapping("/order/modifyAmount")
    public OrderEntity modifyAmount(@RequestBody OrderEntity order) {
        return orderService.modifyAmount(order);
    }

    @DeleteMapping("/order/delete/{orderId}")
    public void deleteOrder(@PathVariable int orderId) {
        orderService.delete(orderId);
    }

    // 모집글 등록
    @PostMapping("/order/register/recruit")
    public void registerRecruit(@RequestBody RecruitEntity recruit) {
        orderService.registerRecruit(recruit);
    }

}
