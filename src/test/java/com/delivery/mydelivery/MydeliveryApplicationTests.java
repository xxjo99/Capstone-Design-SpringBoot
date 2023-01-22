package com.delivery.mydelivery;

import com.delivery.mydelivery.home.CategoryEntity;
import com.delivery.mydelivery.home.HomeService;
import com.delivery.mydelivery.menu.MenuEntity;
import com.delivery.mydelivery.menu.OptionContentEntity;
import com.delivery.mydelivery.menu.OptionEntity;
import com.delivery.mydelivery.menu.MenuService;
import com.delivery.mydelivery.order.OrderEntity;
import com.delivery.mydelivery.order.OrderService;
import com.delivery.mydelivery.recruit.RecruitEntity;
import com.delivery.mydelivery.recruit.RecruitService;
import com.delivery.mydelivery.store.StoreService;
import com.delivery.mydelivery.login.LoginService;
import com.delivery.mydelivery.store.StoreEntity;
import com.delivery.mydelivery.user.UserEntity;
import com.delivery.mydelivery.register.RegisterService;
import com.delivery.mydelivery.user.UserService;
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

	@Autowired
	private HomeService homeService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private RecruitService recruitService;

	@Autowired
	private UserService userService;

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
	void getMenuList() {
		List<MenuEntity> menuList =  menuService.getMenuList(1);

		for (MenuEntity menu : menuList) {
			System.out.println(menu.toString());
		}
	}

	@Test
	void getOptionList() {
		List<OptionEntity> menuOptionList =  menuService.getMenuOptionList(11);

		for (OptionEntity option : menuOptionList) {
			System.out.println(option.toString());
		}
	}

	@Test
	void getOptionContentList() {
		List<OptionContentEntity> menuOptionContentList = menuService.getMenuOptionContentList(1);

		for (OptionContentEntity optionContent : menuOptionContentList) {
			System.out.println(optionContent.toString());
		}
	}

	@Test
	void getCategoryList() {
		List<CategoryEntity> categoryList = homeService.getCategoryList();

		for (CategoryEntity category : categoryList) {
			System.out.println(category.toString());
		}
	}

	@Test
	void findStoreInCart() {
		int userId = 1;
		int storeId = 5;

		List<OrderEntity> orderList = orderService.findStore(userId, storeId);
		for (OrderEntity order : orderList) {
			System.out.println(order.toString());
		}
	}

	@Test
	void addMenu() {
		OrderEntity order = new OrderEntity();

		order.setMenuId(11);
		order.setAmount(1);
		order.setUserId(1);
		order.setTotalPrice(1);
		order.setSelectOption("1");

		orderService.addMenu(order);
	}

	@Test
	void getMenuInCart() {
		int userId = 1;

		List<OrderEntity> orderList = orderService.getOrderList(userId);

		for (OrderEntity order : orderList) {
			System.out.println(order.toString());
		}
	}

	@Test
	void getOptionContentName() {
		String list = "1,2,3,4";
		List<String> result = orderService.getOptionContentList(list);

		for (String name : result) {
			System.out.println(name);
		}
	}

	@Test
	void getMenuName() {
		int menuId = 11;
		System.out.println(menuService.getMenuName(menuId));
	}

	@Test
	void getRecruitList() {
		List<RecruitEntity> recruitList = recruitService.getRecruitList();

		for (RecruitEntity recruit : recruitList) {
			System.out.println(recruit.toString());
		}
	}

	@Test
	void getUser() {
		int userId = 1;
		System.out.println(userService.getUser(userId).toString());
	}

	@Test
	void getParticipantCount() {
		int count = recruitService.getParticipantCount(13);
		System.out.println(count);
	}

	@Test
	void findUserInRecruit() {
		boolean flag = recruitService.findUserInRecruit(1, 2);
		System.out.println(flag);
	}
}
