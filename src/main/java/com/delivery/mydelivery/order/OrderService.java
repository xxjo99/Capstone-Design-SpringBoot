package com.delivery.mydelivery.order;

import com.delivery.mydelivery.menu.OptionContentRepository;
import com.delivery.mydelivery.recruit.*;
import com.delivery.mydelivery.user.SchoolEntity;
import com.delivery.mydelivery.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OptionContentRepository optionContentRepository;

    @Autowired
    private RecruitRepository recruitRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ParticipantOrderRepository participantOrderRepository;

    @Autowired
    private DeliveryPlaceRepository deliveryPlaceRepository;

    // 장바구니에 메뉴 추가
    public OrderEntity addMenu(OrderEntity order) {
        return orderRepository.save(order);
    }

    // 유저id, 매장id를 통해 장바구니에 다른 매장의 메뉴가 들어있는지 확인
    // 존재한다면 true, 존재하지 않는다면 false
    public List<OrderEntity> findStore(int userId, int storeId) {
        return orderRepository.findByUserIdAndStoreIdNot(userId, storeId);
    }

    // 장바구니에 담긴 메뉴들 가져옴
    public List<OrderEntity> getOrderList(int userId) {
        return orderRepository.findByUserId(userId);
    }

    // 입력받은 문자열 형태의 옵션내용id 목록을 옵션내용이름으로 변환해서 반환
    public List<String> getOptionContentList(String optionContentListStr) {
        List<String> optionContentList = Arrays.asList(optionContentListStr.split(","));
        Collections.sort(optionContentList);

        List<String> optionContentResult = new ArrayList<>();
        for (String s : optionContentList) {
            int optionContentId = Integer.parseInt(s);

            String contentName = optionContentRepository.findByMenuOptionContentId(optionContentId).getOptionContentName();
            optionContentResult.add(contentName);
        }

        return optionContentResult;
    }

    // 메뉴 개수 수정
    public OrderEntity modifyAmount(OrderEntity order) {
        return orderRepository.save(order);
    }

    // 메뉴 삭제
    public void delete(int orderId) {
        OrderEntity order = orderRepository.findByOrderId(orderId);
        orderRepository.delete(order);
    }

    // 장소 리스트
    public List<String> getDeliveryPlaceList() {
        List<DeliveryPlaceEntity> deliveryPlaceList = new ArrayList<>();
        Streamable.of(deliveryPlaceRepository.findAll()).forEach(deliveryPlaceList::add);

        List<String> deliveryPlaceListResult = new ArrayList<>();
        for (DeliveryPlaceEntity deliveryPlace : deliveryPlaceList) {
            deliveryPlaceListResult.add(deliveryPlace.getPlace());
        }

        return deliveryPlaceListResult;
    }

    // 모집글 등록
    // 모집글 등록 -> 모집글의 참가자에 추가 -> 참가자의 주문목록 메뉴에 등록 -> 장바구니에서 메뉴 삭제
    @Transactional
    public void registerRecruit(RecruitEntity recruit, String deliveryTime) {

        // 1. 모집글 등록
        Timestamp timestamp = Timestamp.valueOf(deliveryTime);
        recruit.setDeliveryTime(timestamp);
        RecruitEntity recruitResult = recruitRepository.save(recruit);

        // 2. 참가자 추가
        int recruitId = recruitResult.getRecruitId();
        int userId = recruitResult.getUserId();

        ParticipantEntity participant = new ParticipantEntity();

        participant.setRecruitId(recruitId);
        participant.setUserId(userId);
        participant.setParticipantType("registrant");
        participant.setPaymentStatus(0);

        participantRepository.save(participant);

        // 3. 주문목록 등록
        List<OrderEntity> orderList = orderRepository.findByUserId(userId);
        for (OrderEntity order : orderList) {
            ParticipantOrderEntity participantOrder = new ParticipantOrderEntity();

            participantOrder.setRecruitId(recruitId);
            participantOrder.setParticipantId(userId);
            participantOrder.setStoreId(order.getStoreId());
            participantOrder.setMenuId(order.getMenuId());
            participantOrder.setSelectOption(order.getSelectOption());
            participantOrder.setAmount(order.getAmount());
            participantOrder.setTotalPrice(order.getTotalPrice());

            participantOrderRepository.save(participantOrder);
        }

        // 4. 장바구니에서 메뉴 삭제
        for (OrderEntity order : orderList) {
            orderRepository.delete(order);
        }

    }

}