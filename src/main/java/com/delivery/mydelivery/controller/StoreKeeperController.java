package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.StoreKeeper.DeliveryInfoDTO;
import com.delivery.mydelivery.StoreKeeper.StoreKeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StoreKeeperController {

    @Autowired
    private StoreKeeperService storeKeeperService;

    // 결제완료된 모집글 리스트 반환
    @GetMapping("/storeKeeper/recruit/paymentComplete")
    public String getCompletePaymentRecruitList(Model model) {
        List<DeliveryInfoDTO> deliveryInfoList = storeKeeperService.getCompletePaymentRecruitList();
        model.addAttribute("deliveryInfoList", deliveryInfoList);
        return "storeKeeper";
    }
}
