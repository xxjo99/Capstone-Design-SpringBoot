package com.delivery.mydelivery;

import com.delivery.mydelivery.menu.MenuEntity;
import com.delivery.mydelivery.menu.MenuOptionContentEntity;
import com.delivery.mydelivery.menu.MenuOptionEntity;
import com.delivery.mydelivery.menu.MenuService;
import com.delivery.mydelivery.store.StoreService;
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
	private StoreService storeService;

	@Autowired
	private MenuService menuService;

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
	void getStoreList() {
		List<StoreEntity> storeList = new ArrayList<>();

		storeList = storeService.getStoreList("백반, 죽, 국수");

		for (StoreEntity store : storeList) {
			System.out.println(store.toString());
		}
	}

	@Test
	void findStore() {
		StoreEntity store = storeService.getStore(1);
		System.out.println(store.toString());
	}

	@Test
	void getMenuList() {
		List<MenuEntity> menuList =  menuService.getMenuList(1);

		for (MenuEntity menu : menuList) {
			System.out.println(menu.toString());
		}
	}

	@Test
	void getOptionList() {
		List<MenuOptionEntity> menuOptionList =  menuService.getMenuOptionList(11);

		for (MenuOptionEntity option : menuOptionList) {
			System.out.println(option.toString());
		}
	}

	@Test
	void getOptionContentList() {
		List<MenuOptionContentEntity> menuOptionContentList = menuService.getMenuOptionContentList(1);

		for (MenuOptionContentEntity optionContent : menuOptionContentList) {
			System.out.println(optionContent.toString());
		}
	}

}
