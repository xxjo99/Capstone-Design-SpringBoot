package com.delivery.mydelivery.orderHistory;

import com.delivery.mydelivery.recruit.*;
import com.delivery.mydelivery.store.StoreEntity;
import com.delivery.mydelivery.store.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class OrderHistoryService {

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;
    @Autowired
    private OrderHistoryDetailRepository orderHistoryDetailRepository;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private ParticipantOrderRepository participantOrderRepository;
    @Autowired
    private RecruitRepository recruitRepository;
    @Autowired
    private StoreRepository storeRepository;

    // 주문 내역
    public List<OrderHistoryEntity> getOrderHistory(int userId) {
        List<OrderHistoryEntity> orderHistoryList = orderHistoryRepository.findByUserId(userId);

        // 2. 현재시간
        Timestamp now = new Timestamp(System.currentTimeMillis());

        // 3. 현재시간과의 차이가 작은 순으로 정렬 후 반환
        orderHistoryList.sort((o1, o2) -> {
            long diff1 = Math.abs(now.getTime() - o1.getDeliveryDate().getTime());
            long diff2 = Math.abs(now.getTime() - o2.getDeliveryDate().getTime());
            return Long.compare(diff1, diff2);
        });

        return orderHistoryList;
    }

    // 주문내역, 상세주문내역 추가
    @Transactional
    public void addOrderHistory(int recruitId, MultipartFile image) throws IOException {
        RecruitEntity recruit = recruitRepository.findByRecruitId(recruitId);
        List<ParticipantEntity> participantList = participantRepository.findByRecruitId(recruitId);

        for (ParticipantEntity participant : participantList) {
            int userId = participant.getUserId();

            // 주문내역 추가
            OrderHistoryEntity orderHistory = new OrderHistoryEntity();
            orderHistory.setRecruitId(recruitId);
            orderHistory.setUserId(userId);
            orderHistory.setStoreId(recruit.getStoreId());
            orderHistory.setPaymentMoney(participant.getPaymentMoney());
            orderHistory.setParticipantCount(participantList.size());
            orderHistory.setDeliveryCompletePicture(image.getBytes());// 이미지
            orderHistory.setDeliveryDate(recruit.getDeliveryTime());
            orderHistoryRepository.save(orderHistory);

            // 상세주문내역 추가
            List<ParticipantOrderEntity> participantOrderList = participantOrderRepository.findByRecruitIdAndParticipantId(recruitId, userId);
            for (ParticipantOrderEntity participantOrder : participantOrderList) {
                OrderHistoryDetailEntity orderHistoryDetail = new OrderHistoryDetailEntity();
                orderHistoryDetail.setRecruitId(recruitId);
                orderHistoryDetail.setUserId(userId);
                orderHistoryDetail.setStoreId(recruit.getStoreId());
                orderHistoryDetail.setMenuId(participantOrder.getMenuId());
                orderHistoryDetail.setSelectOption(participantOrder.getSelectOption());
                orderHistoryDetail.setAmount(participantOrder.getAmount());
                orderHistoryDetail.setTotalPrice(participantOrder.getTotalPrice());
                orderHistoryDetailRepository.save(orderHistoryDetail);
            }

        }

    }

    // 절약한 배달비
    public int getSaveMoney(int recruitId, int userId) {
        OrderHistoryEntity orderHistory = orderHistoryRepository.findByRecruitIdAndUserId(recruitId, userId);
        int participantCount = orderHistory.getParticipantCount();
        StoreEntity store = storeRepository.findByStoreId(orderHistory.getStoreId());
        int deliveryTip = Integer.parseInt(store.getDeliveryTip());

        return deliveryTip / participantCount;
    }

    // 상세주문내역
    public List<OrderHistoryDetailEntity> getOrderHistoryDetail(int recruitId, int userId) {
        return orderHistoryDetailRepository.findByRecruitIdAndUserId(recruitId, userId);
    }

    // 배달 완료 이미지
    public byte[] getImage(int recruitId, int userId) {
        OrderHistoryEntity orderHistory = orderHistoryRepository.findByRecruitIdAndUserId(recruitId, userId);
        return orderHistory.getDeliveryCompletePicture();
    }

}
