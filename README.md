# 캡스톤 디자인 - 배달비 공유 시스템 BackEnd


## 개요
- SpringBoot와 안드로이드 스튜디오를 통한 배달비 공유 앱 프로젝트의 서버

 __제작기간__
- 22.12.30 ~ 23.05.25


## 사용 기술 및 개발 환경
- Server : Apache Tomcat 9.0.69
- DB : MySql
- Framework : SpringBoot, JPA, Mustache
- Language : JAVA, HTML5
- Tool : IntelliJ, GitHub

## 구현기능
- 앱에서 수행할 기능의 api 구현

### 주요 api
- 인증번호 전송 api : RegisterController.java
```
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
```

- 로그인 api : LoginController.java, LoginService.java
```
@GetMapping("/login/{email}/{pw}")
public UserEntity login(@PathVariable String email, @PathVariable String pw) {
    return loginService.login(email, pw);
}
```

```
public UserEntity login(String email, String pw) {
    return loginRepository.findByEmailAndPw(email, pw);
}
```

- 장바구니에 메뉴 추가 api : OrderController.java, OrderService.java
```
@PostMapping("/order/save")
public OrderEntity addMenu(@RequestBody OrderEntity order) {
    return orderService.addMenu(order);
}
```

```
public OrderEntity addMenu(OrderEntity order) {
    return orderRepository.save(order);
}
```

- 모집글 작성 api : OrderController.java, OrderService.java
```
@PostMapping("/order/register/recruit")
public void registerRecruit(@RequestBody RecruitEntity recruit, @RequestParam("deliveryTime") String deliveryTime) {
    orderService.registerRecruit(recruit, deliveryTime);
}
```

```
@Transactional
public void registerRecruit(RecruitEntity recruit, String deliveryTime) {

    // 1. 모집글 등록
    Timestamp timestamp = Timestamp.valueOf(deliveryTime);
    recruit.setDeliveryTime(timestamp);
    RecruitEntity recruitResult = recruitRepository.save(recruit);

    // 2. 참가자 추가
    int recruitId = recruitResult.getRecruitId();
    int userId = recruitResult.getUserId();

    ParticipantEntity participant = new ParticipantEntity();

    participant.setRecruitId(recruitId);
    participant.setUserId(userId);
    participant.setParticipantType("registrant");
    participant.setPaymentStatus(0);

    participantRepository.save(participant);

    // 3. 주문목록 등록
    List<OrderEntity> orderList = orderRepository.findByUserId(userId);
    for (OrderEntity order : orderList) {
        ParticipantOrderEntity participantOrder = new ParticipantOrderEntity();

        participantOrder.setRecruitId(recruitId);
        participantOrder.setParticipantId(userId);
        participantOrder.setStoreId(order.getStoreId());
        participantOrder.setMenuId(order.getMenuId());
        participantOrder.setSelectOption(order.getSelectOption());
        participantOrder.setAmount(order.getAmount());
        participantOrder.setTotalPrice(order.getTotalPrice());

        participantOrderRepository.save(participantOrder);
    }

    // 4. 장바구니에서 메뉴 삭제
    for (OrderEntity order : orderList) {
        orderRepository.delete(order);
    }

}
```

- 결제 api : RecruitController.java, RecruitService.java
```
@PostMapping("/recruit/payment")
public void payment(@RequestParam("recruitId") int recruitId, @RequestParam("userId") int userId, @RequestParam("usedPoint") int usedPoint, @RequestParam("content") String content) {
    recruitService.payment(recruitId, userId, usedPoint, content);
}
```

```
@Transactional
public void payment(int recruitId, int userId, int usedPoint, String content) {
    // 1. 결제 금액 추가, 결제 완료로 변경
    ParticipantEntity participant = participantRepository.findByRecruitIdAndUserId(recruitId, userId);
    participant.setPaymentMoney(usedPoint);
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
    pointHistory.setContent(content + " 결제");
    pointHistory.setDateTime(Timestamp.valueOf(LocalDateTime.now()));
    pointHistoryRepository.save(pointHistory);
}
```

## ERD
![그림1](https://github.com/xxjo99/Capstone-Design-SpringBoot/assets/96373083/fe4e9a1f-70a9-4c86-84ff-33dde98f5419)
