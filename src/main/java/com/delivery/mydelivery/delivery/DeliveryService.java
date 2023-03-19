package com.delivery.mydelivery.delivery;

import com.delivery.mydelivery.recruit.RecruitEntity;
import com.delivery.mydelivery.recruit.RecruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private RecruitRepository recruitRepository;

    // 접수된 주문 리스트
    public List<RecruitEntity> getDeliveryList() {
        // 1. 모든 모집글 검색
        List<RecruitEntity> recruitList = new ArrayList<>();
        Streamable.of(recruitRepository.findAll()).forEach(recruitList::add);

        // 2. 접수된 주문만 검색 후 반환
        List<RecruitEntity> recruitListResult = new ArrayList<>();
        for (RecruitEntity recruit : recruitList) {
            int receiptState = recruit.getReceiptState();

            if (receiptState == 1) {
                recruitListResult.add(recruit);
            }
        }

        return recruitListResult;
    }

    // 모집글 배달 상태 배달 시작으로 변경
    public void setDeliveryStateStartDelivery(int recruitId) {
        RecruitEntity recruit = recruitRepository.findByRecruitId(recruitId);
        recruit.setReceiptState(2);
        recruitRepository.save(recruit);
    }

    // 배달이 출발한 모집글 리스트
    public List<RecruitEntity> getStartedDeliveryList() {
        // 1. 모든 모집글 검색
        List<RecruitEntity> recruitList = new ArrayList<>();
        Streamable.of(recruitRepository.findAll()).forEach(recruitList::add);

        // 2. 배달이 출발한 모집글만 검색 후 반환
        List<RecruitEntity> recruitListResult = new ArrayList<>();
        for (RecruitEntity recruit : recruitList) {
            int receiptState = recruit.getReceiptState();

            if (receiptState == 2) {
                recruitListResult.add(recruit);
            }
        }

        return recruitListResult;
    }

}
