package com.Project.Backend.service;

import com.Project.Backend.entity.Employee;
import com.Project.Backend.entity.Position;
import com.Project.Backend.exception.BaseException;
import com.Project.Backend.exception.EmployeeException;
import com.Project.Backend.model.MFileUploadResponse;
import com.Project.Backend.repository.EmployeeRepository;
import com.Project.Backend.util.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    //สร้างฟังชั่นหาด้วย email
    public Optional<Employee> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    //สร้างฟังชั่นหาด้วย id
    public Optional<Employee> findById(String id) {
        return employeeRepository.findById(id);
    }

    //create
    public Employee createEmployee(String email, String firstName, String lastName, String gender, Integer phoneNumber, Position position,
                                   Integer salary, String street, String city, String state, Integer zipcode) throws BaseException {

        // validate

        if (Objects.isNull(firstName)) {
            //throw error firstName null
            throw EmployeeException.createFirstNameNull();

        }
        if (Objects.isNull(lastName)) {
            //throw error lastName null
            throw EmployeeException.createLastNameNull();

        }

        if (Objects.isNull(gender)) {
            //throw error gender null
            throw EmployeeException.createGenderNull();

        }

        if (Objects.isNull(email)) {
            //throw error email null
            throw EmployeeException.createEmailNull();

        }

        if (Objects.isNull(phoneNumber)) {
            //throw error phoneNumber null
            throw EmployeeException.createPhoneNumberNull();

        }

        if (Objects.isNull(position)) {
            //throw error position null
            throw EmployeeException.createPositionNull();

        }

        if (Objects.isNull(salary)) {
            //throw error salary null
            throw EmployeeException.createSalaryNull();
        }

        if (Objects.isNull(street)) {
            //throw error street null
            throw EmployeeException.createStreetNull();

        }

        if (Objects.isNull(city)) {
            //throw error city null
            throw EmployeeException.createCityNull();

        }

        if (Objects.isNull(state)) {
            //throw error state null
            throw EmployeeException.createStateNull();

        }

        if (Objects.isNull(zipcode)) {
            //throw error zipcode null
            throw EmployeeException.createZipcodeNull();

        }

        //verify
        if (employeeRepository.existsByEmail(email)) {
            throw EmployeeException.emailDuplicate();

        }

        //save
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setGender(gender);
        employee.setEmail(email);
        employee.setPhoneNumber(phoneNumber);
        employee.setPosition(position);
        employee.setSalary(salary);
        employee.setStreet(street);
        employee.setCity(city);
        employee.setState(state);
        employee.setZipcode(zipcode);

        return employeeRepository.save(employee);
    }

    //Update (ฟังชั่นรวม)
    public Employee update(Employee employee) {
        return employeeRepository.save(employee);

    }

    public Employee updateEmployee(String id, String firstName, String lastName, String gender, String email,
                                   Integer phoneNumber, Integer salary, String street, String city,
                                   String state, Integer zipcode) throws BaseException {

        Optional<Employee> opt = employeeRepository.findById(id);

        if (opt.isEmpty()) {
            // throw error not found
            throw EmployeeException.notFound();
        }
        Employee employee = opt.get();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setGender(gender);
        employee.setEmail(email);
        employee.setPhoneNumber(phoneNumber);
        employee.setSalary(salary);
        employee.setStreet(street);
        employee.setCity(city);
        employee.setState(state);
        employee.setZipcode(zipcode);

        return employeeRepository.save(employee);
    }

    //listUser
    public List<Employee> listEmployee() {
        List<Employee> employees = (List<Employee>) employeeRepository.findAll();

        return employees;
    }

    //GetEmployeeById
    public Employee getEmployee(String id) throws EmployeeException {
        Optional<Employee> opt = employeeRepository.findById(id);
        if (opt.isEmpty()) {
            // throw error not found
            throw EmployeeException.notFound();

        }
        Employee employee = opt.get();

        return employee;
    }

    //upload
    public Employee updateEmployeePicture(String id ,String picture) throws BaseException {

        Optional<Employee> opt = employeeRepository.findById(id);

        if (opt.isEmpty()) {
            // throw error not found
            throw EmployeeException.notFound();
        }
        Employee employee = opt.get();

        employee.setPicture(picture);

        return employeeRepository.save(employee);
    }


    //Delete
    public void deleteEmployeeById(String id) {
        employeeRepository.deleteById(id);
    }


}
