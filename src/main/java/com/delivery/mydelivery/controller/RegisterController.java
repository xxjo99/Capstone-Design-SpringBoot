package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.user.UserEntity;
import com.delivery.mydelivery.register.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import java.util.Random;

@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @Autowired
    private JavaMailSender mailSender;

    // 이메일 중복검사
    @GetMapping("/register/check/{email}")
    public boolean duplicateEmailCk(@PathVariable String email) {
        return registerService.findUserByEmail(email);
    }

    // 이메일 인증번호 전송
    @GetMapping("/register/send/{email}")
    public String sendAuthNum(@PathVariable String email) throws Exception {

        Random random = new Random(); // 난수 생성
        int authNum = random.nextInt(888888) + 111111; // 111111~999999

        /* 이메일 보내기 */
        String setFrom = "xxjo4221@gmail.com"; // 자신의 이메일
        String toMail = email; // 보낼 이메일 주소
        String title = "회원가입 인증 이메일 입니다."; // 이메일 제목
        String content = // 내용
                "인증 번호는 " + authNum + "입니다." +
                        "<br>" +
                        "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String result = Integer.toString(authNum); // 전송된 인증번호를 문자열로 변환 후 반환
        return result;
    }

    // 회원가입
    @PostMapping("/register") // 저장할 객체를 @RequestBody로 설정
    public UserEntity register(@RequestBody UserEntity user) {
        return registerService.save(user);
    }

}
