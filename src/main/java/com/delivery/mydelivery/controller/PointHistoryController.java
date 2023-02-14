package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.point.PointHistoryEntity;
import com.delivery.mydelivery.point.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PointHistoryController {

    @Autowired
    private PointService pointService;

    // 포인트 충전내역 추가
    @PostMapping("/point/add/history")
    public void addPointHistory(@RequestBody PointHistoryEntity pointHistory) {
        pointService.addPointHistory(pointHistory);
    }

    // 포인트 이용내역
    @GetMapping("/point/history/{userId}")
    public List<PointHistoryEntity> getPointHistory(@PathVariable int userId) {
        return pointService.getPointHistory(userId);
    }
}
