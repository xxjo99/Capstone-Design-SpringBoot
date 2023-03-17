package com.delivery.mydelivery.StoreKeeper;

import com.delivery.mydelivery.menu.MenuEntity;
import com.delivery.mydelivery.menu.MenuRepository;
import com.delivery.mydelivery.menu.OptionContentRepository;
import com.delivery.mydelivery.recruit.*;
import com.delivery.mydelivery.store.StoreEntity;
import com.delivery.mydelivery.store.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    @Autowired
    private OptionContentRepository optionContentRepository;
    @Autowired
    private MenuRepository menuRepository;

    public List<DeliveryInfoDTO> getCompletePaymentRecruitList() {
        // 1. 모든 모집글 검색
        List<RecruitEntity> recruitList = new ArrayList<>();
        Streamable.of(recruitRepository.findAll()).forEach(recruitList::add);

        // 2. 주문대기중이고, 모든 인원이 결제가 완료된 모집글만 필터링
        List<RecruitEntity> completePaymentRecruitList = new ArrayList<>();
        for (RecruitEntity recruit : recruitList) {
            int receiptState = recruit.getReceiptState();

            if (receiptState == 0) {
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

    // 해당 모집글에서 주문한 모든 메뉴
    public List<OrderDTO> getOrderList(int recruitId) {
        // 1. 해당 모집글에서 주문한 모든 메뉴 검색
        List<ParticipantOrderEntity> participantOrderList = participantOrderRepository.findByRecruitId(recruitId);

        // 2. 반환할 데이터가 담긴 객체 생성
        List<OrderDTO> orderList = new ArrayList<>();

        // 3. 데이터 변환, 저장
        for (ParticipantOrderEntity participantOrder : participantOrderList) {
            OrderDTO order = new OrderDTO();

            order.setRecruitId(recruitId);
            order.setAmount(participantOrder.getAmount());
            order.setTotalPrice(participantOrder.getTotalPrice());

            // 메뉴이름 검색 후 저장
            MenuEntity menu = menuRepository.findByMenuId(participantOrder.getMenuId());
            String menuName = menu.getMenuName();
            order.setMenuName(menuName);

            // 옵션목룍 변환 후 저장
            String selectOptionList = getOptionContentList(participantOrder.getSelectOption());
            order.setSelectOption(selectOptionList);

            // 리스트에 추가
            orderList.add(order);
        }

        return orderList;
    }

    // 입력받은 문자열 형태의 옵션내용id 목록을 옵션내용이름으로 변환해서 반환
    public String getOptionContentList(String optionContentListStr) {
        List<String> optionContentList = Arrays.asList(optionContentListStr.split(","));
        Collections.sort(optionContentList);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < optionContentList.size(); i++) {
            int optionContentId = Integer.parseInt(optionContentList.get(i));
            String contentName = optionContentRepository.findByMenuOptionContentId(optionContentId).getOptionContentName();

            if (i != optionContentList.size() - 1) {
                result.append(contentName).append(", ");
            } else {
                result.append(contentName);
            }
        }

        return result.toString();
    }

    // 주문 접수
    public void receiptOrder(int recruitId) {
        RecruitEntity recruit = recruitRepository.findByRecruitId(recruitId);
        recruit.setReceiptState(1);
        recruitRepository.save(recruit);
    }

}
