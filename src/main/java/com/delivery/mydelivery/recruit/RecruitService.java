package com.delivery.mydelivery.recruit;

import com.delivery.mydelivery.store.StoreEntity;
import com.delivery.mydelivery.store.StoreRepository;
import com.delivery.mydelivery.store.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecruitService {

    @Autowired
    private RecruitRepository recruitRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ParticipantOrderRepository participantOrderRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreService storeService;

    public List<RecruitEntity> getRecruitList(String registrantPlace) {
        List<RecruitEntity> recruitList = new ArrayList<>();
        recruitList = recruitRepository.findByRegistrantPlace(registrantPlace);
        return recruitList;
    }

    // 해당 사용자의 등록글이 있는지 검색, 있다면 false 없다면 true
    public Boolean findRecruit(int userId) {
        RecruitEntity recruit = recruitRepository.findByUserId(userId);

        // 없을경우
        return recruit == null;
    }

    // 해당 매장의 참가인원 반환
    public int getParticipantCount(int recruitId) {
        List<ParticipantEntity> participantList = participantRepository.findByRecruitId(recruitId);
        return participantList.size();
    }

    // 해당모집글에 해당 유저가 존재한다면 true 반환
    public boolean findUserInRecruit(int recruitId, int userId) {
        ParticipantEntity participant = participantRepository.findByRecruitIdAndUserId(recruitId, userId);
        return participant != null;
    }

    // 해당글에 참가
    public ParticipantEntity participate(ParticipantEntity participant) {
        return participantRepository.save(participant);
    }

    // 해당 유저가 참가한 글 검색
    public List<RecruitEntity> findRecruitList(int userId) {

        List<ParticipantEntity> participantList = participantRepository.findByUserId(userId);

        List<RecruitEntity> recruitList = new ArrayList<>();
        for (ParticipantEntity participant : participantList) {
            int recruitId = participant.getRecruitId();
            recruitList.add(recruitRepository.findByRecruitId(recruitId));
        }

        return recruitList;
    }

    // 해당 글에 참가한 참가자 리스트 반환
    public List<ParticipantEntity> getParticipantList(int recruitId) {
        return participantRepository.findByRecruitId(recruitId);
    }

    // 사용자가 담은 메뉴의 총 금액을 반환
    public int getOrdersTotalPrice(int recruitId, int participantId) {
        List<ParticipantOrderEntity> participantOrderList = participantOrderRepository.findByRecruitIdAndParticipantId(recruitId, participantId);

        int totalPrice = 0;

        for (ParticipantOrderEntity participantOrder : participantOrderList) {
            totalPrice += participantOrder.getTotalPrice();
        }

        return totalPrice;
    }

    // 자신을 제외한 나머지 참가자 리스트 반환
    public List<ParticipantEntity> getParticipantListExceptMine(int recruitId, int userId) {
        List<ParticipantEntity> participantList = participantRepository.findByRecruitId(recruitId);
        List<ParticipantEntity> participantListResult = new ArrayList<>();

        for (ParticipantEntity participant : participantList) {
            int participantId = participant.getUserId();

            if (participantId != userId) {
                participantListResult.add(participant);
            }
        }

        return participantListResult;
    }

    // 추가한 메뉴, 배달비를 포함한 최종결제금액 반환
    public int getFinalPayment(int recruitId, int storeId, int userId) {
        // 참여자수
        int participantCount = getParticipantCount(recruitId);

        // 1인당 배달팁
        StoreEntity store = storeRepository.findByStoreId(storeId);
        int deliveryTip = Integer.parseInt(store.getDeliveryTip()) / participantCount;

        // 사용자가 담은 메뉴들의 총 금액
        int totalPrice = getOrdersTotalPrice(recruitId, userId);

        // 최종결제금액
        return (deliveryTip + totalPrice);
    }

    // 해당 글에서 해당 유저가 담은 메뉴 반환
    public List<ParticipantOrderEntity> getOrderList(int recruitId, int participantId) {
        return participantOrderRepository.findByRecruitIdAndParticipantId(recruitId, participantId);
    }

    // 개수 수정
    public ParticipantOrderEntity modifyAmount(ParticipantOrderEntity participantOrder) {
        return participantOrderRepository.save(participantOrder);
    }

    // 메뉴 삭제
    public void deleteMenu(int participantOrderId) {
        ParticipantOrderEntity order = participantOrderRepository.findByParticipantOrderId(participantOrderId);
        participantOrderRepository.delete(order);
    }

    // 메뉴 추가
    public ParticipantOrderEntity addMenu(ParticipantOrderEntity order) {
        return participantOrderRepository.save(order);
    }

    // 모집글 검색
    public List<RecruitEntity> searchRecruit(String keyword, String deliveryAvailablePlace) {
        // 1. 매장리스트 검색
        List<StoreEntity> storeList = storeService.searchStore(keyword, deliveryAvailablePlace);

        // 2. 검색된 매장리스트의 id로 모집글 검색 후 반환
        List<RecruitEntity> recruitList = new ArrayList<>();
        for (StoreEntity store : storeList) {
            List<RecruitEntity> recruitResult = recruitRepository.findByStoreId(store.getStoreId());
            recruitList.addAll(recruitResult);
        }

        return recruitList;
    }

    // 모집글 삭제
    public void deleteRecruit(int recruitId) {
        // 1. 모집글에 유저들이 담은 메뉴들 삭제
        List<ParticipantOrderEntity> participantOrderList = participantOrderRepository.findByRecruitId(recruitId);
        for (ParticipantOrderEntity participantOrder : participantOrderList) {
            participantOrderRepository.delete(participantOrder);
        }

        // 2. 모집글에 등록된 유저 삭제
        List<ParticipantEntity> participantList = participantRepository.findByRecruitId(recruitId);
        for (ParticipantEntity participant : participantList) {
            participantRepository.delete(participant);
        }

        // 3. 모집글 삭제
        RecruitEntity recruit = recruitRepository.findByRecruitId(recruitId);
        recruitRepository.delete(recruit);
    }

    // 모집글의 등록자 검색
    public ParticipantEntity findRegistrant(int recruitId) {
        List<ParticipantEntity> participantList = participantRepository.findByRecruitId(recruitId);

        ParticipantEntity participantResult = new ParticipantEntity();
        for (ParticipantEntity participant : participantList) {
            if (participant.getParticipantType().equals("registrant")) {
                participantResult = participant;
            }
        }

        return participantResult;
    }

}
