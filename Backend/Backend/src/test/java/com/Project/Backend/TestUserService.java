package com.Project.Backend;

import com.Project.Backend.entity.User;
import com.Project.Backend.exception.BaseException;
import com.Project.Backend.service.UserService;
import com.Project.Backend.util.SecurityUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestUserService {

	@Autowired
	private UserService userService;


	@Test
	@Order(1)
	void testCreate() throws BaseException {
		String token = SecurityUtil.generateToken();
		User user = userService.create(
				TestData.email,
				TestData.password,
				TestData.firstName,
				TestData.lastName,
				token,
				new Date()
		);

		//Check not null
		Assertions.assertNotNull(user); // สร้างแล้ว ต้องไม่ null
		Assertions.assertNotNull(user.getId()); // User ต้อองมี primary key

		//Check equals
		Assertions.assertEquals(TestData.email, user.getEmail());

		boolean isMatch = userService.matchPassword(TestData.password, user.getPassword());
		Assertions.assertTrue(isMatch);

		Assertions.assertEquals(TestData.firstName, user.getFirstName());
		Assertions.assertEquals(TestData.lastName, user.getLastName());

	}

	@Test
	@Order(2)
	void update() throws BaseException {
		Optional<User> opt = userService.findByEmail(TestData.email);
		Assertions.assertTrue(opt.isPresent()); // เช็ค otp ไม่ต้อง empty

		User user = opt.get();

		User updatedUser = userService.updateUser(user.getId(), TestUpdate.firstName, TestData.lastName);

		//check
		Assertions.assertNotNull(updatedUser); //ไม่ null
		Assertions.assertEquals(TestUpdate.firstName, updatedUser.getFirstName()); // ชื่อที่บันทึกลง database เหมือนกันกับที่ user แก้ข้ามา (updatedUser)

	}


	@Test
	@Order(3)
	void testDelete() {
		Optional<User> opt = userService.findByEmail(TestData.email);
		Assertions.assertTrue(opt.isPresent()); // เช็ค otp ไม่ต้อง empty

		User user = opt.get();
		userService.deleteById(user.getId());

		Optional<User> otpDelete = userService.findByEmail(TestData.email); //หา email หลังจาก delete แล้ว (ตรวจว่าลบจริงมั้ย)
		Assertions.assertTrue(otpDelete.isEmpty()); // ลบไปแล้วต้องไม้มี (ใช้ได้ 2 ฟังชั่น isEmpty, isPresent )

	}

	interface TestData {
		String email = "Test12312344@Test.com";
		String password = "1234";
		String firstName = "Grodon";
		String lastName = "MacGriude";
	}

	interface TestUpdate {
		String firstName = "Gazz";
		String lastName = "Duo";

	}

	interface AtkTestCreateData {
		String date = "15-04-2565";
		String status = "Negative";
		String annotation = "";
	}

	interface AtkTestCreateData2 {
		String date = "18-04-2565";
		String status = "Negative";
		String annotation = "";
	}


}

