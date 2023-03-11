package com.delivery.mydelivery.firebase;

import com.delivery.mydelivery.recruit.ParticipantEntity;
import com.delivery.mydelivery.recruit.ParticipantRepository;
import com.delivery.mydelivery.user.UserEntity;
import com.delivery.mydelivery.user.UserRepository;
import com.fasterxml.jackson.core.JsonParseException;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FirebaseCloudMessageService {

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/delivery-ecfb9/messages:send";
    private final ObjectMapper objectMapper;

    @Autowired
    ParticipantRepository participantRepository;
    @Autowired
    UserRepository userRepository;

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

    // 배달 접수 알림 전송
    private void sendMessageDeliveryReception(int recruitId) throws IOException {

        // 1. 모집글에 참가한 유저 리스트 검색
        List<ParticipantEntity> participantList = participantRepository.findByRecruitId(recruitId);

        // 2. 유저의 기기토큰 저장
        List<String> tokenList = new ArrayList<>();
        for (ParticipantEntity participant : participantList) {
            UserEntity user = userRepository.findByUserId(participant.getUserId());
            tokenList.add(user.getToken());
        }

        // 3. 메시지 전송
        String title = "배달 접수";
        String body = "배달이 접수되었습니다.";
        for (String token : tokenList) {
            sendMessage(token, title, body);
        }

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

    private String makeMessage(String targetToken, String title, String body) throws JsonParseException, JsonProcessingException {
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