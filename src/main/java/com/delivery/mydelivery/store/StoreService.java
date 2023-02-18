package com.delivery.mydelivery.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepoistory;

    // 카테고리, 배달가능지역을 통해 오픈한 매장 검색
    public List<StoreEntity> getOpenedStoreList(String category, String deliveryAvailablePlace) {
        List<StoreEntity> storeList = storeRepoistory.findByCategoryAndDeliveryAvailablePlace(category, deliveryAvailablePlace);

        LocalTime currentTime = LocalTime.now(); // 현재 시간

        List<StoreEntity> storeListResult = new ArrayList<>();
        for (StoreEntity store : storeList) {
            String deliveryAvailable = store.getDeliveryAvailablePlace();

            if (deliveryAvailable.equals(deliveryAvailablePlace)) {
                // 현재시간이 매장의 오픈시간에 맞는지 확인
                Timestamp startTime = store.getOpenTime();
                Timestamp closeTime = store.getCloseTime();

                int startHour = startTime.getHours();
                int startMin = startTime.getMinutes();
                int closeHour = closeTime.getHours();
                int closeMin = closeTime.getMinutes();

                LocalTime openTime = LocalTime.of(startHour, startMin, 0);
                LocalTime endTime = LocalTime.of(closeHour, closeMin, 0);

                if (currentTime.isAfter(openTime) && currentTime.isBefore(endTime)) {
                    storeListResult.add(store);
                }
            }
        }

        return storeListResult;
    }

    // 카테고리, 배달가능지역을 통해 매장 검색
    public List<StoreEntity> getClosedStoreList(String category, String deliveryAvailablePlace) {
        List<StoreEntity> storeList = storeRepoistory.findByCategoryAndDeliveryAvailablePlace(category, deliveryAvailablePlace);

        LocalTime currentTime = LocalTime.now(); // 현재 시간

        List<StoreEntity> storeListResult = new ArrayList<>();
        for (StoreEntity store : storeList) {
            // 현재시간이 매장의 오픈시간에 맞는지 확인
            Timestamp startTime = store.getOpenTime();
            Timestamp closeTime = store.getCloseTime();

            int startHour = startTime.getHours();
            int startMin = startTime.getMinutes();
            int closeHour = closeTime.getHours();
            int closeMin = closeTime.getMinutes();

            LocalTime openTime = LocalTime.of(startHour, startMin, 0);
            LocalTime endTime = LocalTime.of(closeHour, closeMin, 0);

            // 맞다면 추가
            if (currentTime.isBefore(openTime) || currentTime.isAfter(endTime)) {
                storeListResult.add(store);
            }
        }

        return storeListResult;
    }

    public StoreEntity getStore(int storeId) {
        return storeRepoistory.findByStoreId(storeId);
    }

    // 열려있는 매장 검색
    public List<StoreEntity> searchOpenStore(String keyword, String deliveryAvailablePlace) {
        List<StoreEntity> storeList = storeRepoistory.findByStoreNameContaining(keyword);

        LocalTime currentTime = LocalTime.now(); // 현재 시간

        List<StoreEntity> storeListResult = new ArrayList<>();
        for (StoreEntity store : storeList) {
            String deliveryAvailable = store.getDeliveryAvailablePlace();

            if (deliveryAvailable.equals(deliveryAvailablePlace)) {

                // 현재시간이 매장의 오픈시간에 맞는지 확인
                Timestamp startTime = store.getOpenTime();
                Timestamp closeTime = store.getCloseTime();

                int startHour = startTime.getHours();
                int startMin = startTime.getMinutes();
                int closeHour = closeTime.getHours();
                int closeMin = closeTime.getMinutes();

                LocalTime openTime = LocalTime.of(startHour, startMin, 0);
                LocalTime endTime = LocalTime.of(closeHour, closeMin, 0);

                if (currentTime.isAfter(openTime) && currentTime.isBefore(endTime)) {
                    storeListResult.add(store);
                }
            }
        }

        return storeListResult;
    }


}
