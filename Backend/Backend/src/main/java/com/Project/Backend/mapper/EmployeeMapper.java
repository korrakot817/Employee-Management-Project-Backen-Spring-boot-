package com.Project.Backend.mapper;

import com.Project.Backend.entity.Employee;
import com.Project.Backend.model.employeeModel.MEmployeeProfileResponse;
import com.Project.Backend.model.employeeModel.MEmployeeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface EmployeeMapper {

    MEmployeeResponse toMEmployeeResponse(Employee employee);

    MEmployeeProfileResponse toMEmployeeProfileResponse(Employee employee);


}
