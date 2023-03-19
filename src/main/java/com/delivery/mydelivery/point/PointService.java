package com.delivery.mydelivery.point;

import com.delivery.mydelivery.recruit.RecruitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class PointService {

    @Autowired
    private PointHistoryRepository pointHistoryRepository;

    // 포인트내역 추가
    public void addPointHistory(PointHistoryEntity pointHistory) {
        pointHistory.setDateTime(Timestamp.valueOf(LocalDateTime.now()));
        pointHistoryRepository.save(pointHistory);
    }

    // 포인트 이용내역
    public List<PointHistoryEntity> getPointHistory(int userId) {
        List<PointHistoryEntity> pointHistoryList = pointHistoryRepository.findByUserId(userId);

        // 2. 현재시간
        Timestamp now = new Timestamp(System.currentTimeMillis());

        // 3. 현재시간과의 차이가 작은 순으로 정렬 후 반환
        pointHistoryList.sort((o1, o2) -> {
            long diff1 = Math.abs(now.getTime() - o1.getDateTime().getTime());
            long diff2 = Math.abs(now.getTime() - o2.getDateTime().getTime());
            return Long.compare(diff1, diff2);
        });

        return pointHistoryList;
    }

}
