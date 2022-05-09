package com.Project.Backend;

import com.Project.Backend.business.EmailBusiness;
import com.Project.Backend.exception.BaseException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestEmailBusiness {

	@Autowired
	private EmailBusiness emailBusiness;

	@Test
	@Order(1)
	void testSendActiveEmail() throws BaseException {
		emailBusiness.sendActivateUserEmail(TestData.email,
				TestData.firstName,
				TestData.lastName,
				TestData.token);

	}

	interface TestData {
		String email = "rawpiez@gmail.com";
		String firstName = "Loius";
		String lastName = "Postman";
		String token = "m#1212312121";
	}

}

