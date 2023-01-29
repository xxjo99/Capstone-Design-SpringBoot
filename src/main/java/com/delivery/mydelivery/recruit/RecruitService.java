package com.delivery.mydelivery.recruit;

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
}
