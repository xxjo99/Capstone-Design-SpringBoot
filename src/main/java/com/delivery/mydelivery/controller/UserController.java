package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.user.ParticipationRestrictionEntity;
import com.delivery.mydelivery.user.UserEntity;
import com.delivery.mydelivery.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Random;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    // 유저 검색
    @GetMapping("/user/find/id/{userId}")
    public UserEntity getUser(@PathVariable int userId) {
        return userService.getUser(userId);
    }

    // 모든 학교 검색
    @GetMapping("/user/schools")
    public List<String> getAllSchool() {
        return userService.getAllSchool();
    }

    // 인증번호 전송 - 비밀번호 찾기
    @GetMapping("/user/pw/{email}")
    public String sendAuthNum(@PathVariable String email) throws Exception {

        Random random = new Random(); // 난수 생성
        int authNum = random.nextInt(888888) + 111111; // 111111~999999

        /* 이메일 보내기 */
        String setFrom = "xxjo4221@gmail.com"; // 자신의 이메일
        String title = "인증 이메일 입니다."; // 이메일 제목
        String content = // 내용
                "인증 번호는 " + authNum + "입니다." +
                        "<br>" +
                        "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(email);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Integer.toString(authNum);
    }

    // 이메일로 유저 검색
    @GetMapping("/user/find/email/{email}")
    public UserEntity findUser(@PathVariable String email) {
        return userService.findUser(email);
    }

    // 정보 수정
    @PostMapping("/user/modify")
    public UserEntity modify(@RequestBody UserEntity user) {
        return userService.modify(user);
    }

    // 이용제한 생성
    @PostMapping("/user/restriction/set/period")
    public void setParticipationRestriction(@RequestParam("userId") int userId) {
        userService.setParticipationRestriction(userId);
    }

    // 이용제한 반환
    @GetMapping("/user/restriction/get/{userId}")
    public ParticipationRestrictionEntity getParticipationRestriction(@PathVariable int userId) {
        return userService.getParticipationRestriction(userId);
    }

    // 이용제한 확인
    @GetMapping("/user/restriction/check/{userId}")
    public Boolean checkRestriction(@PathVariable int userId) {
        return userService.checkRestriction(userId);
    }

    // 포인트 차감
    @PostMapping("/user/deduct/point")
    public void deductPoint(@RequestParam("userId") int userId, @RequestParam("deductPoint") int deductPoint) {
        userService.deductPoint(userId, deductPoint);
    }

}
