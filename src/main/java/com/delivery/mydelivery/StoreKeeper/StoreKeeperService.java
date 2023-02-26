package com.delivery.mydelivery.StoreKeeper;

import com.delivery.mydelivery.recruit.*;
import com.delivery.mydelivery.store.StoreEntity;
import com.delivery.mydelivery.store.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreKeeperService {

    @Autowired
    private RecruitRepository recruitRepository;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private ParticipantOrderRepository participantOrderRepository;
    @Autowired
    private StoreRepository storeRepository;

    public List<DeliveryInfoDTO> getCompletePaymentRecruitList() {
        // 1. 모든 모집글 검색
        List<RecruitEntity> recruitList = new ArrayList<>();
        Streamable.of(recruitRepository.findAll()).forEach(recruitList::add);

        // 2. 모든 인원이 결제가 완료된 모집글만 필터링
        List<RecruitEntity> completePaymentRecruitList = new ArrayList<>();
        for (RecruitEntity recruit : recruitList) {
            int recruitId = recruit.getRecruitId();

            List<ParticipantEntity> participantList = participantRepository.findByRecruitId(recruitId);

            boolean checkPaymentStatus = true;
            for (ParticipantEntity participant : participantList) {
                int paymentStatus = participant.getPaymentStatus();

                if (paymentStatus != 1) {
                    checkPaymentStatus = false;
                    break;
                }
            }

            if (checkPaymentStatus) {
                completePaymentRecruitList.add(recruit);
            }

        }

        // 형식을 바꿔서 반환
        List<DeliveryInfoDTO> deliveryInfoList = new ArrayList<>();
        for (RecruitEntity recruit : completePaymentRecruitList) {
            int recruitId = recruit.getRecruitId();
            String storeName = getStoreName(recruit.getStoreId());
            int menuAmount = getMenuAmount(recruitId);
            String school = recruit.getRegistrantPlace();
            String deliveryPlace = recruit.getPlace();

            DeliveryInfoDTO deliveryInfo = new DeliveryInfoDTO();
            deliveryInfo.setRecruitId(recruitId);
            deliveryInfo.setStoreName(storeName);
            deliveryInfo.setMenuAmount(menuAmount);
            deliveryInfo.setSchool(school);
            deliveryInfo.setDeliveryPlace(deliveryPlace);

            deliveryInfoList.add(deliveryInfo);
        }

        return deliveryInfoList;
    }

    // 해당 매장의 메뉴 개수
    public int getMenuAmount(int recruitId) {
        List<ParticipantOrderEntity> participantOrderList = participantOrderRepository.findByRecruitId(recruitId);
        return participantOrderList.size();
    }

    // 매장 이름
    public String getStoreName(int storeId) {
        StoreEntity store = storeRepository.findByStoreId(storeId);
        return store.getStoreName();
    }

}
