package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.StoreKeeper.DeliveryInfoDTO;
import com.delivery.mydelivery.StoreKeeper.OrderDTO;
import com.delivery.mydelivery.StoreKeeper.StoreKeeperService;
import com.delivery.mydelivery.recruit.ParticipantOrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class StoreKeeperController {

    @Autowired
    private StoreKeeperService storeKeeperService;

    // 결제완료된 모집글 리스트 반환
    @GetMapping("/storeKeeper/recruit/paymentCompleteList")
    public String getCompletePaymentRecruitList(Model model) {
        List<DeliveryInfoDTO> deliveryInfoList = storeKeeperService.getCompletePaymentRecruitList();
        model.addAttribute("deliveryInfoList", deliveryInfoList);
        return "storeKeeper";
    }

    // 모집글에서 주문한 모든 메뉴 리스트
    @GetMapping("/storeKeeper/orderList/{recruitId}")
    public String getOrderList(@PathVariable int recruitId, Model model) {
        List<OrderDTO> orderList = storeKeeperService.getOrderList(recruitId);
        model.addAttribute("orderList", orderList);
        return "orderList";
    }

}
