package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.StoreKeeper.DeliveryInfoDTO;
import com.delivery.mydelivery.StoreKeeper.OrderDTO;
import com.delivery.mydelivery.StoreKeeper.StoreKeeperService;
import com.delivery.mydelivery.firebase.FirebaseCloudMessageService;
import com.delivery.mydelivery.recruit.RecruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@RestController
public class StoreKeeperController {

    @Autowired
    private StoreKeeperService storeKeeperService;
    @Autowired
    private FirebaseCloudMessageService firebaseCloudMessageService;
    @Autowired
    private RecruitService recruitService;

    // 결제완료된 모집글 리스트 반환
    @GetMapping("/storeKeeper/recruit/paymentCompleteList")
    public ModelAndView getCompletePaymentRecruitList(Model model) {
        List<DeliveryInfoDTO> deliveryInfoList = storeKeeperService.getCompletePaymentRecruitList();
        model.addAttribute("deliveryInfoList", deliveryInfoList);
        return new ModelAndView("storeKeeper");
    }

    // 모집글에서 주문한 모든 메뉴 리스트
    @GetMapping("/storeKeeper/orderList/{recruitId}")
    public ModelAndView getOrderList(@PathVariable int recruitId, Model model) {
        List<OrderDTO> orderList = storeKeeperService.getOrderList(recruitId);
        model.addAttribute("recruitId", recruitId);
        model.addAttribute("orderList", orderList);
        return new ModelAndView("orderList");
    }

    // 배달접수, 알림전송
    @PostMapping("/storeKeeper/receipt/{recruitId}")
    public void receiptOrder(@PathVariable int recruitId) throws IOException {
        // 1. 배달 접수 완료 알림 전송
        firebaseCloudMessageService.sendMessageReceipt(recruitId);
        // 2. 배달 접수
        storeKeeperService.receiptOrder(recruitId);
    }

    // 배달 거부, 알림 전송
    @PostMapping("/storeKeeper/refuse/{recruitId}")
    public void refuseOrder(@PathVariable int recruitId) throws IOException {
        // 1. 거부 알림 전송
        firebaseCloudMessageService.sendMessageRefuse(recruitId);
        // 2. 포인트 반환, 환불내역 추가
        storeKeeperService.refuseOrder(recruitId);
        // 3. 모집글 삭제
        recruitService.deleteRecruit(recruitId);
    }

}
