package com.Project.Backend.business;

import com.Project.Backend.entity.Employee;
import com.Project.Backend.exception.BaseException;
import com.Project.Backend.exception.EmployeeException;
import com.Project.Backend.exception.FileException;
import com.Project.Backend.mapper.EmployeeMapper;
import com.Project.Backend.model.MFileUploadResponse;
import com.Project.Backend.model.employeeModel.MEmployeeCreateRequest;
import com.Project.Backend.model.employeeModel.MEmployeeProfileResponse;
import com.Project.Backend.model.employeeModel.MEmployeeResponse;
import com.Project.Backend.model.employeeModel.MUpdateEmployeeRequest;
import com.Project.Backend.service.EmployeeService;
import com.Project.Backend.util.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeBusiness {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeeBusiness(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    //createEmployee
    public MEmployeeResponse createEmployee(MEmployeeCreateRequest request) throws BaseException {

        Employee employee = employeeService.createEmployee(
                request.getFirstName(),
                request.getLastName(),
                request.getGender(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getPosition(),
                request.getSalary(),
                request.getStreet(),
                request.getCity(),
                request.getState(),
                request.getZipcode()
        );

        return employeeMapper.toMEmployeeResponse(employee);
    }

    //listEmployee
    public List<Employee> employeeLists() {
        List<Employee> response = employeeService.listEmployee();

        return response;
    }

    //getById
    public Employee getEmployeeProfile(String id) throws BaseException {
        Employee employee = employeeService.getEmployee(id);

        return employee;
    }

    //updateEmployee
    public MEmployeeProfileResponse updateEmployee(MUpdateEmployeeRequest request, String id) throws BaseException {
        Optional<Employee> opt = employeeService.findById(id);
        if (opt.isEmpty()) {
            throw EmployeeException.notFound();
        }

        Employee employee = opt.get();

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setSalary(request.getSalary());

        // validate
        if (ObjectUtils.isEmpty(request.getFirstName())) {
            throw EmployeeException.updateFirstNameNull();
        }

        if (ObjectUtils.isEmpty(request.getLastName())) {
            throw EmployeeException.updateLastNameNull();
        }

        if (ObjectUtils.isEmpty(request.getSalary())) {
            throw EmployeeException.updateSalaryNull();
        }

        employeeService.update(employee);

        return employeeMapper.toMEmployeeProfileResponse(employee);

    }

    //uploadFile
    public MFileUploadResponse upload(String id, MultipartFile multipartFile) throws IOException, BaseException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();

        //validate file
        if (multipartFile == null) { //หาค่าไฟล์
            // ถ้าไม่มี throw error ออกไป
            throw FileException.fileNull();
        }

        //validate size
        if (multipartFile.getSize() > 1048576 * 2) { //หาขนาดไฟล์ เราตั้งไว้ที่ 2 MB
            //  ถ้าเกิน 2 MB throw error
            throw FileException.fileMaxSize();
        }
        //validate type file
        String contentType = multipartFile.getContentType();
        if (contentType == null) {
            //throw error
            throw FileException.unsupported();
        }

        List<String> supportedTypes = Arrays.asList("image/jpeg", "image/png");
        if (!supportedTypes.contains(contentType)) {
            // throw error (unsupport)
            throw FileException.unsupported();
        }

        String filecode = FileUploadUtil.saveFile(fileName, multipartFile);

        MFileUploadResponse response = new MFileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/assets/Files-Upload/" + filecode + "-" + fileName);

        employeeService.updateEmployeePicture(id, response.getDownloadUri());

        return response;

    }

    //deleteById
    public void deleteById(String id) {
        employeeService.deleteEmployeeById(id);

    }
}
