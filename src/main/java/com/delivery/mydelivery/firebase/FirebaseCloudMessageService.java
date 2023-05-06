package com.delivery.mydelivery.firebase;

import com.delivery.mydelivery.keyword.KeywordEntity;
import com.delivery.mydelivery.keyword.KeywordRepository;
import com.delivery.mydelivery.recruit.ParticipantEntity;
import com.delivery.mydelivery.recruit.ParticipantRepository;
import com.delivery.mydelivery.recruit.RecruitEntity;
import com.delivery.mydelivery.recruit.RecruitRepository;
import com.delivery.mydelivery.store.StoreEntity;
import com.delivery.mydelivery.store.StoreRepository;
import com.delivery.mydelivery.user.UserEntity;
import com.delivery.mydelivery.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FirebaseCloudMessageService {

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/delivery-ecfb9/messages:send";
    private final ObjectMapper objectMapper;

    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecruitRepository recruitRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private KeywordRepository keywordRepository;

    // 알림 전송
    public void sendMessage(String targetToken, String title, String body) throws IOException {
        String message = makeMessage(targetToken, title, body);

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();
    }

    // 삭제알림 전송
    public void sendMessageDeleteRecruit(int recruitId) throws IOException {
        // 1. 모집글에 참가한 유저 리스트 검색
        List<ParticipantEntity> participantList = participantRepository.findByRecruitId(recruitId);

        // 2. 등록자를 제외한 참가자의 토큰만 저장
        List<String> tokenList = new ArrayList<>();
        for (ParticipantEntity participant : participantList) {
            String participantType = participant.getParticipantType();

            if (!participantType.equals("registrant")) {
                UserEntity user = userRepository.findByUserId(participant.getUserId());
                tokenList.add(user.getToken());
            }
        }

        // 3. 메시지 전송
        String title = "삭제";
        String body = "모집글이 삭제되었습니다.";
        for (String token : tokenList) {
            sendMessage(token, title, body);
        }

    }

    // 배달접수알림 전송
    public void sendMessageReceipt(int recruitId) throws IOException {
        // 메시지 내용
        RecruitEntity recruit = recruitRepository.findByRecruitId(recruitId);
        StoreEntity store = storeRepository.findByStoreId(recruit.getStoreId());
        String storeName = store.getStoreName();
        String title = "주문이 접수되었습니다.";
        String body = storeName + "에서 주문하신 배달이 접수되었습니다.";

        // 1. 모집글에 참가한 유저 리스트 검색
        List<ParticipantEntity> participantList = participantRepository.findByRecruitId(recruitId);

        // 2. 참가한 모든 인원에게 메시지 전송
        for (ParticipantEntity participant : participantList) {
            UserEntity user = userRepository.findByUserId(participant.getUserId());
            sendMessage(user.getToken(), title, body);
        }
    }

    // 배달거부알림 전송
    public void sendMessageRefuse(int recruitId) throws IOException {
        // 메시지 내용
        String title = "주문이 취소되었습니다.";
        String body = "현재 가게에서 주문 접수가 불가능하여 주문이 취소되었습니다.";

        // 1. 모집글에 참가한 유저 리스트 검색
        List<ParticipantEntity> participantList = participantRepository.findByRecruitId(recruitId);

        // 2. 참가한 모든 인원에게 메시지 전송
        for (ParticipantEntity participant : participantList) {
            UserEntity user = userRepository.findByUserId(participant.getUserId());
            sendMessage(user.getToken(), title, body);
        }
    }

    // 배달시작알림 전송
    public void sendMessageStartDelivery(int recruitId) throws IOException {
        // 메시지 내용
        String title = "픽업완료";
        String body = "주문하신 배달이 시작되었습니다.";

        // 1. 모집글에 참가한 유저 리스트 검색
        List<ParticipantEntity> participantList = participantRepository.findByRecruitId(recruitId);

        // 2. 참가한 모든 인원에게 메시지 전송
        for (ParticipantEntity participant : participantList) {
            UserEntity user = userRepository.findByUserId(participant.getUserId());
            sendMessage(user.getToken(), title, body);
        }
    }

    // 배달 완료 알림 전송
    public void sendMessageCompleteDelivery(int recruitId) throws IOException {
        // 메시지 내용
        String title = "배달 완료";
        String body = "지정된 장소로 배달이 완료되었습니댜. 주문내역을 확인해주세요";

        // 1. 모집글에 참가한 유저 리스트 검색
        List<ParticipantEntity> participantList = participantRepository.findByRecruitId(recruitId);

        // 2. 참가한 모든 인원에게 메시지 전송
        for (ParticipantEntity participant : participantList) {
            UserEntity user = userRepository.findByUserId(participant.getUserId());
            sendMessage(user.getToken(), title, body);
        }
    }

    // 등록한 키워드 알림 전송
    public void sendKeywordMessage(String keyword, String storeName) throws IOException {
        // 메시지 내용
        String title = keyword + "키워드 알림";
        String body = storeName + "에 대한 모집글이 등록되었습니다.";

        // 1. 해당 키워드를 등록한 유저 검색
        List<KeywordEntity> keywordList = keywordRepository.findByKeyword(keyword);

        // 2. 메시지 전송
        for (KeywordEntity keywordEntity : keywordList) {
            UserEntity user = userRepository.findByUserId(keywordEntity.getUserId());
            sendMessage(user.getToken(), title, body);
        }

    }

    // 메시지 생성
    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        ).build()).validateOnly(false).build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/firebase_service_key.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}