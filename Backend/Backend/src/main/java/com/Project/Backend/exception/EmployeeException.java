package com.Project.Backend.exception;

public class EmployeeException extends BaseException{
    public EmployeeException(String code) {
        super("employee." + code);
    }

    //Create Exception
    public static EmployeeException createEmailNull(){
        return new EmployeeException("create.email.null");
    }

    public static EmployeeException emailDuplicate(){
        return new EmployeeException("create.email.duplicate");
    }

    public static EmployeeException createFirstNameNull(){
        return new EmployeeException("create.firstName.null");
    }

    public static EmployeeException createLastNameNull(){
        return new EmployeeException("create.lastName.null");
    }

    public static EmployeeException createGenderNull(){
        return new EmployeeException("create.gender.null");
    }

    public static EmployeeException createPositionNull(){
        return new EmployeeException("create.position.null");
    }
    public static EmployeeException createSalaryNull() {
        return new EmployeeException("create.salary.null");
    }

    public static EmployeeException createPhoneNumberNull() {
        return new EmployeeException("create.phoneNumber.null");
    }

    public static EmployeeException createStreetNull() {
        return new EmployeeException("create.street.null");
    }

    public static EmployeeException createCityNull() {
        return new EmployeeException("create.city.null");
    }

    public static EmployeeException createStateNull() {
        return new EmployeeException("create.state.null");
    }

    public static EmployeeException createZipcodeNull() {
        return new EmployeeException("create.zipcode.null");
    }


    //Update Employee
    public static EmployeeException notFound(){
        return new EmployeeException("not.found");
    }

    public static EmployeeException updateFirstNameNull(){
        return new EmployeeException("update.firstName.null");
    }

    public static EmployeeException updateLastNameNull(){
        return new EmployeeException("update.lastName.null");
    }

    public static EmployeeException updateSalaryNull(){
        return new EmployeeException("update.salary.null");
    }



}
