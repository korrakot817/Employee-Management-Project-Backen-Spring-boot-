package com.Project.Backend.exception;

public class UserException extends BaseException{
    public UserException(String code) {
        super("user." + code);
    }

    //Create Exception
    public static UserException createEmailNull(){
        return new UserException("create.email.null");
    }

    public static UserException emailDuplicate(){
        return new UserException("create.email.duplicate");
    }

    public static UserException createPasswordNull(){
        return new UserException("create.password.null");
    }

    public static UserException createFirstNameNull(){
        return new UserException("create.first.name.null");
    }

    public static UserException createLastNameNull(){
        return new UserException("create.last.name.null");
    }

    //Update Exception
    public static UserException notFound(){
        return new UserException("not.found");
    }

    public static UserException updateFirstNameNull(){
        return new UserException("update.firstName.null");
    }

    public static UserException updateLastNameNull(){
        return new UserException("update.lastName.null");
    }

    public static UserException getNotFound(){
        return new UserException("get.not.found");
    }

    //login Exception
    public static UserException loginFailEmailNotFound(){
        return new UserException("login.fail");
    }

    public static UserException loginFailUserUnactivated(){
        return new UserException("login.fail.user.unactivated");
    }

    public static UserException loginFailPasswordIncorrect(){
        return new UserException("login.fail");
    }

    //token Exception
    public static UserException unauthorized(){
        return new UserException("unauthorized");
    }

    //Activate Exception
    public static UserException activateNoToken(){
        return new UserException("no.active.token");
    }

    public static UserException activationFail(){
        return new UserException("activation.fail");
    }

    public static  UserException activateTokenExpired(){
        return new UserException("activate.token.expired");
    }

    public static UserException activateAlready(){
        return new UserException("activate.already");
    }

    //ResendActivationEmail Exception
    public static UserException resendActivationNoToken(){
        return new UserException("resend.activation.no.token");
    }

    public static UserException resendActivationTokenNotFound(){
        return new UserException("resend.activation.fail");
    }





}
