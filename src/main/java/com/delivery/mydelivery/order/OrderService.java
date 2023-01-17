package com.delivery.mydelivery.order;

import com.delivery.mydelivery.menu.OptionContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OptionContentRepository optionContentRepository;

    @Autowired
    private RecruitRepository recruitRepository;

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
        for (int i = 0; i < optionContentList.size(); i++) {
            int optionContentId = Integer.parseInt(optionContentList.get(i));

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

    // 모집글 등록
    public RecruitEntity registerRecruit(RecruitEntity recruit) {
        return recruitRepository.save(recruit);
    }

}
