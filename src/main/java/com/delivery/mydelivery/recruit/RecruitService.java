package com.delivery.mydelivery.recruit;

import com.delivery.mydelivery.point.PointHistoryEntity;
import com.delivery.mydelivery.point.PointHistoryRepository;
import com.delivery.mydelivery.store.StoreEntity;
import com.delivery.mydelivery.store.StoreRepository;
import com.delivery.mydelivery.store.StoreService;
import com.delivery.mydelivery.user.UserEntity;
import com.delivery.mydelivery.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PointHistoryRepository pointHistoryRepository;

    // 모집글 리스트
    public List<RecruitEntity> getRecruitList(String registrantPlace) {

        List<RecruitEntity> recruitList = recruitRepository.findByRegistrantPlace(registrantPlace);

        // 현재시간 이후의 모집글만 표시
        LocalDateTime currentTime = LocalDateTime.now(); // 현재 시간

        List<RecruitEntity> recruitListResult = new ArrayList<>();
        for (RecruitEntity recruit : recruitList) {
            Timestamp timestamp = recruit.getDeliveryTime();
            LocalDateTime deliveryTime = timestamp.toLocalDateTime();

            if (currentTime.isBefore(deliveryTime)) {
                recruitListResult.add(recruit);
            }
        }

        return recruitListResult;
    }

    // 모집글 리스트, 마감시간이 가까운 순부터 정렬
    public List<RecruitEntity> getRecruitListOrder(String registrantPlace) {

        // 1. 모집글 리스트 저장
        List<RecruitEntity> recruitList = getRecruitList(registrantPlace);

        // 2. 현재시간
        Timestamp now = new Timestamp(System.currentTimeMillis());

        // 3. 현재시간과의 차이가 작은 순으로 정렬
        recruitList.sort((o1, o2) -> {
            long diff1 = Math.abs(now.getTime() - o1.getDeliveryTime().getTime());
            long diff2 = Math.abs(now.getTime() - o2.getDeliveryTime().getTime());
            return Long.compare(diff1, diff2);
        });

        return recruitList;
    }

    // 해당 사용자의 등록글이 있는지 검색, 있다면 false 없다면 true
    public Boolean findRecruit(int userId) {
        RecruitEntity recruit = recruitRepository.findByUserId(userId);

        // 사용자의 등록글이 없을경우 true 반환
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
        participant.setPaymentStatus(0);
        return participantRepository.save(participant);
    }

    // 모집글 정보
    public RecruitEntity getRecruit(int recruitId) {
        return recruitRepository.findByRecruitId(recruitId);
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
        List<StoreEntity> storeList = storeService.searchOpenStore(keyword, deliveryAvailablePlace);

        // 2. 검색된 매장리스트의 id로 모집글 검색 후 반환
        List<RecruitEntity> recruitList = new ArrayList<>();
        for (StoreEntity store : storeList) {
            List<RecruitEntity> recruitResult = recruitRepository.findByStoreId(store.getStoreId());
            recruitList.addAll(recruitResult);
        }

        // 3. 현재시간 이후의 모집글만 검색
        LocalDateTime currentTime = LocalDateTime.now(); // 현재 시간

        List<RecruitEntity> recruitListResult = new ArrayList<>();
        for (RecruitEntity recruit : recruitList) {
            Timestamp timestamp = recruit.getDeliveryTime();
            LocalDateTime deliveryTime = timestamp.toLocalDateTime();

            if (currentTime.isBefore(deliveryTime)) {
                recruitListResult.add(recruit);
            }
        }

        return recruitListResult;
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

    // 모집글 탈퇴
    public void leaveRecruit(int recruitId, int userId) {
        // 1. 해당 유저가 담은 메뉴 삭제
        List<ParticipantOrderEntity> participantOrderList = participantOrderRepository.findByRecruitIdAndParticipantId(recruitId, userId);
        for (ParticipantOrderEntity participantOrder : participantOrderList) {
            participantOrderRepository.delete(participantOrder);
        }

        // 2. 유저 삭제
        ParticipantEntity participant = participantRepository.findByRecruitIdAndUserId(recruitId, userId);
        participantRepository.delete(participant);
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

    // 해당모집글에 참가한 유저 반환
    public ParticipantEntity getParticipant(int recruitId, int userId) {
        return participantRepository.findByRecruitIdAndUserId(recruitId, userId);
    }

    // 결제하기
    @Transactional
    public void payment(int recruitId, int userId, int usedPoint, String content) {
        // 1. 결제 완료로 변경
        ParticipantEntity participant = participantRepository.findByRecruitIdAndUserId(recruitId, userId);
        participant.setPaymentStatus(1);
        participantRepository.save(participant);

        // 2. 보유 포인트 변경
        UserEntity user = userRepository.findByUserId(userId);
        int currentPoint = user.getPoint();
        user.setPoint(currentPoint - usedPoint);
        userRepository.save(user);

        // 3. 포인트 사용내역 추가
        PointHistoryEntity pointHistory = new PointHistoryEntity();
        pointHistory.setUserId(userId);
        pointHistory.setPoint(usedPoint);
        pointHistory.setType("사용");
        pointHistory.setBalance(currentPoint - usedPoint);
        pointHistory.setContent(content);
        pointHistory.setDateTime(Timestamp.valueOf(LocalDateTime.now()));
        pointHistoryRepository.save(pointHistory);
    }

    // 마감시간이 지나고, 결제가 완료되지 않은 유저가 있다면 포인트 차감 후 강퇴
    @Transactional
    public void checkParticipantPaymentStatus(int recruitId) {
        // 1. 모집글 검색
        RecruitEntity recruit = recruitRepository.findByRecruitId(recruitId);

        // 2. 배달 매장 검색 후 해당 매장의 배달료 반환
        StoreEntity store = storeRepository.findByStoreId(recruit.getStoreId());
        int deliveryTip = Integer.parseInt(store.getDeliveryTip());

        // 3. 해당 모집글에 참가한 유저 검색
        List<ParticipantEntity> participantList = participantRepository.findByRecruitId(recruitId);
        int participantCount = participantList.size(); // 모집글의 참가인원 수

        // 4. 인당 지불할 배달비 계산
        int finalDeliveryTip = deliveryTip / participantCount;

        // 5. 결제하지 않은 유저 검색
        List<ParticipantEntity> incompleteParticipantList = new ArrayList<>();
        for (ParticipantEntity participant : participantList) {
            int status = participant.getPaymentStatus();

            if (status == 0) {
                incompleteParticipantList.add(participant);
            }
        }

        // 6. 유저가 담은 메뉴 모두 삭제, 포인트 차감, 강퇴
        for (ParticipantEntity participant : incompleteParticipantList) {
            // 1. 메뉴 삭제
            List<ParticipantOrderEntity> participantOrderList = participantOrderRepository.findByRecruitIdAndParticipantId(recruitId, participant.getUserId());

            for (ParticipantOrderEntity participantOrder : participantOrderList) {
                participantOrderRepository.delete(participantOrder);
            }

            // 2. 포인트 차감
            UserEntity user = userRepository.findByUserId(participant.getUserId());
            user.setPoint(user.getPoint() - finalDeliveryTip);
            userRepository.save(user);

            // 3. 강퇴
            participantRepository.delete(participant);
        }

    }


}
