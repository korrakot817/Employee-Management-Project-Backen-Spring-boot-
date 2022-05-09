package com.Project.Backend.api;

import com.Project.Backend.business.EmployeeBusiness;
import com.Project.Backend.entity.Employee;
import com.Project.Backend.entity.Position;
import com.Project.Backend.exception.BaseException;
import com.Project.Backend.model.MFileUploadResponse;
import com.Project.Backend.model.employeeModel.*;
import com.Project.Backend.service.EmployeeService;
import com.Project.Backend.service.PositionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeApi {

    private final EmployeeService employeeService;

    private final PositionService positionService;
    private final EmployeeBusiness employeeBusiness;

    public EmployeeApi(EmployeeService employeeService, PositionService positionService, EmployeeBusiness employeeBusiness) {
        this.employeeService = employeeService;
        this.positionService = positionService;
        this.employeeBusiness = employeeBusiness;
    }


    @PostMapping("/create-employee")
    public ResponseEntity<MEmployeeResponse> createEmployee(@RequestBody MEmployeeRequestPosition request) throws BaseException {

        MEmployeeCreateRequest requestNew = new MEmployeeCreateRequest();
        Optional<Position> opt = positionService.findById(request.getPosition());

        Position position = opt.get();

        requestNew.setFirstName(request.getFirstName());
        requestNew.setLastName(request.getLastName());
        requestNew.setGender(request.getGender());
        requestNew.setEmail(request.getEmail());
        requestNew.setPhoneNumber(request.getPhoneNumber());
        requestNew.setPosition(position);
        requestNew.setSalary(request.getSalary());
        requestNew.setStreet(request.getStreet());
        requestNew.setCity(request.getCity());
        requestNew.setState(request.getState());
        requestNew.setZipcode(request.getZipcode());

        MEmployeeResponse response = employeeBusiness.createEmployee(requestNew);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> employeeLists() {
        List<Employee> response = employeeBusiness.employeeLists();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployee
            (@PathVariable(value = "id") String id) throws BaseException {
        Employee response = employeeBusiness.getEmployeeProfile(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/employee/edit/{id}")
    public ResponseEntity<MEmployeeProfileResponse> updateEmployee
            (@PathVariable(value = "id") String id, @RequestBody MUpdateEmployeeRequest request) throws BaseException {
        MEmployeeProfileResponse response = employeeBusiness.updateEmployee(request, id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/uploadFile/{id}")
    public ResponseEntity<MFileUploadResponse> uploadFile(@PathVariable(value = "id") String id, @RequestParam("file") MultipartFile multipartFile)
            throws IOException, BaseException {
        MFileUploadResponse response = employeeBusiness.upload(id, multipartFile);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(value = "id") String id) {
        employeeBusiness.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).build();

    }

}
