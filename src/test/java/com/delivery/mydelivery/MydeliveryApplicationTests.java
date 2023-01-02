package com.delivery.mydelivery;

import com.delivery.mydelivery.category.CategoryService;
import com.delivery.mydelivery.login.LoginService;
import com.delivery.mydelivery.store.StoreEntity;
import com.delivery.mydelivery.user.UserEntity;
import com.delivery.mydelivery.register.RegisterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest

class MydeliveryApplicationTests {

	@Autowired
	private RegisterService registerService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private CategoryService categoryService;

	@Test
	void addUser() {
		UserEntity user = new UserEntity();

		user.setEmail("email");
		user.setPw("pw");
		user.setName("name");
		user.setBirth("birth");
		user.setPhoneNum("phone");

		registerService.save(user);
	}

	@Test
	void findUserByEmail() {
		boolean flag = registerService.findUserByEmail("emai");
		System.out.println(flag);
	}

	@Test
	void login() {
		UserEntity user = new UserEntity();

		user = loginService.login("이메일", "비밀번호");
		System.out.println(user);
	}

	@Test
	void findStoreByCategory() {
		List<StoreEntity> stores = new ArrayList<>();

		stores = categoryService.findStore("피자");

		for (int i = 0; i < stores.size(); i++) {
			System.out.println(stores.get(i));
		}
	}


}
