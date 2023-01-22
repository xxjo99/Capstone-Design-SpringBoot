package com.delivery.mydelivery.recruit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecruitService {

    @Autowired
    private RecruitRepository recruitRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    public List<RecruitEntity> getRecruitList() {
        List<RecruitEntity> recruitList = new ArrayList<>();
        Streamable.of(recruitRepository.findAll()).forEach(recruitList::add);
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

}
