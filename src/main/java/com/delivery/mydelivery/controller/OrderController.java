package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.order.DeliveryPlaceEntity;
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

    // 장바구니에 선택한 메뉴 추가
    @PostMapping("/order/save")
    public OrderEntity addMenu(@RequestBody OrderEntity order) {
        return orderService.addMenu(order);
    }

    // 유저id, 매장id를 통해 장바구니에 다른 매장의 메뉴가 들어있는지 확인
    @GetMapping("/order/stores/{userId}/{storeId}")
    public List<OrderEntity> findStoreInCart(@PathVariable int userId, @PathVariable int storeId) {
        return orderService.findStore(userId, storeId);
    }

    // 장바구니에 담긴 메뉴들 가져옴
    @GetMapping("/order/orders/{userId}")
    public List<OrderEntity> getOrderList(@PathVariable int userId) {
        return orderService.getOrderList(userId);
    }

    // 입력받은 문자열 형태의 옵션내용id 목록을 옵션내용이름으로 변환해서 반환
    @GetMapping("/order/contents/{contentIdList}")
    public List<String> getContentNameList(@PathVariable String contentIdList) {
        return orderService.getOptionContentList(contentIdList);
    }

    // 메뉴 개수 수정
    @PostMapping("/order/modify/amount")
    public OrderEntity modifyAmount(@RequestBody OrderEntity order) {
        return orderService.modifyAmount(order);
    }

    // 메뉴 삭제
    @DeleteMapping("/order/delete/{orderId}")
    public void deleteOrder(@PathVariable int orderId) {
        orderService.delete(orderId);
    }

    // 장소 리스트
    @GetMapping("/order/place")
    public List<String> getDeliveryPlaceList() {
        return orderService.getDeliveryPlaceList();
    }

    // 모집글 등록
    @PostMapping("/order/register/recruit")
    public void registerRecruit(@RequestBody RecruitEntity recruit, @RequestParam("deliveryTime") String deliveryTime) {
        orderService.registerRecruit(recruit, deliveryTime);
    }

}
