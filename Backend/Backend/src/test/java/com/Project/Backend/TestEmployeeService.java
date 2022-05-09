package com.Project.Backend;

import com.Project.Backend.entity.Employee;
import com.Project.Backend.entity.Position;
import com.Project.Backend.entity.User;
import com.Project.Backend.exception.BaseException;
import com.Project.Backend.service.EmployeeService;
import com.Project.Backend.service.PositionService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Log4j2
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestEmployeeService {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	PositionService positionService;


	@Test
	@Order(2)
	void updateEmployee() throws BaseException {
		Optional<Employee> opt = employeeService.findByEmail(TestEmployeeData.email);
		Assertions.assertTrue(opt.isPresent()); // เช็ค otp ไม่ต้อง empty

		Employee employee = opt.get();

		Employee updateEmployee = employeeService.updateEmployee(employee.getId(),
				TestUpdateEmployee.firstName,
				TestUpdateEmployee.lastName,
				TestUpdateEmployee.gender,
				TestUpdateEmployee.email,
				TestUpdateEmployee.phoneNumber,
				TestUpdateEmployee.salary,
				TestUpdateEmployee.street,
				TestUpdateEmployee.city,
				TestUpdateEmployee.state,
				TestUpdateEmployee.zipcode


		);

		//check
		Assertions.assertNotNull(updateEmployee); //ไม่ null
		Assertions.assertEquals(TestUpdateEmployee.firstName, updateEmployee.getFirstName()); // ชื่อที่บันทึกลง database เหมือนกันกับที่ user แก้ข้ามา (updatedUser)

	}

	@Test
	@Order(4)
	void testDelete() {
		Optional<Employee> opt = employeeService.findByEmail(TestEmployeeData.email);
		Assertions.assertTrue(opt.isPresent()); // เช็ค otp ไม่ต้อง empty

		Employee employee = opt.get();
		System.out.println(employee);

		employeeService.deleteEmployeeById(employee.getId());
		Optional<Employee> otpDelete = employeeService.findByEmail(TestEmployeeData.email); //หา email หลังจาก delete แล้ว (ตรวจว่าลบจริงมั้ย)
		Assertions.assertTrue(otpDelete.isEmpty()); // ลบไปแล้วต้องไม้มี (ใช้ได้ 2 ฟังชั่น isEmpty, isPresent )

	}

	interface TestEmployeeData {



		String firstName = "GG";
		String lastName = "Ez";
		String gender = "gender";
		String email = "GGezGGGG@Test.com";
		Integer phoneNumber = 222222222;
		Integer salary = 26000;
		String street = "street";
		String city = "city";
		String state = "state";
		Integer zipcode = 10250;

	}

	interface TestAddPosition {
		String position = "graphic";

	}

	interface TestUpdateEmployee {
		String firstName = "asdsadasda";
		String lastName = "upasdasdasdate";

		String gender = "asdasdasd";

		String email = "TestUpdate@Test.com";
		Integer phoneNumber = 999999999;
		Integer salary = 25000;
		String street = "phahonyotin";
		String city = "auasdasdtin";
		String state = "asdsad";
		Integer zipcode = 25400;



	}

}

