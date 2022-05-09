package com.Project.Backend.business;

import com.Project.Backend.entity.User;
import com.Project.Backend.exception.BaseException;
import com.Project.Backend.exception.UserException;
import com.Project.Backend.mapper.UserMapper;
import com.Project.Backend.model.*;
import com.Project.Backend.service.TokenService;
import com.Project.Backend.service.UserService;
import com.Project.Backend.util.SecurityUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserBusiness {

    private final UserService userService;
    private final UserMapper userMapper;
    private final TokenService tokenService;
    private final EmailBusiness emailBusiness;


    public UserBusiness(UserService userService, UserMapper userMapper, TokenService tokenService, EmailBusiness emailBusiness) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.emailBusiness = emailBusiness;

    }

    //Register
    public MRegisterResponse register(MRegisterRequest request) throws BaseException {
        String token = SecurityUtil.generateToken();
        User user = userService.create
                (request.getEmail(),
                        request.getPassword(),
                        request.getFirstName(),
                        request.getLastName(),
                        token,
                        nextXMinute(30));

        sendEmail(user);

        return userMapper.toMRegisterResponse(user);
    }

    //ฟังชั่น ส่ง email activation
    private void sendEmail(User user) {
        String token = user.getToken();


        try {
            emailBusiness.sendActivateUserEmail(user.getEmail(), user.getFirstName(), user.getLastName(), token);
        } catch (BaseException e) {
            throw new RuntimeException(e);
        }

    }

    //login
    public MLoginResponse login(@RequestBody MLoginRequest request) throws BaseException {
        //validate request

        //verify database
        Optional<User> opt = userService.findByEmail(request.getEmail()); // หา email ว่ามีใน database มั้ย
        if (opt.isEmpty()) {
            //throw error login fail
            throw UserException.loginFailEmailNotFound();

        }

        User user = opt.get(); // ถ้าเจอ get ออกมาก

        //verity password
        // เอามาตรวจ password ที่ กรอกมาถูกมั้ย
        if (!userService.matchPassword(request.getPassword(), user.getPassword())) { // ถ้า password ไม่ตรงกัน
            //throw error login fail
            throw UserException.loginFailPasswordIncorrect();
        }
        //Validate activate status
        if (!user.isActivated()) {
            //throw error
            throw UserException.loginFailUserUnactivated();
        }

        MLoginResponse response = new MLoginResponse();
        response.setToken(tokenService.tokenize(user));

        return response;
    }

    //Activate account
    public MActivateResponse activate(MActivateRequest request) throws BaseException {
        String token = request.getToken();
        if (StringUtil.isNullOrEmpty(token)) {
            throw UserException.activateNoToken();
        }

        Optional<User> opt = userService.findByToken(token);
        if (opt.isEmpty()) {
            throw UserException.activationFail();
        }

        User user = opt.get();

        if (user.isActivated()) {
            throw UserException.activateAlready();
        }

        Date now = new Date();
        Date expireDate = user.getTokenExpire();
        if (now.after(expireDate)) {
            throw UserException.activateTokenExpired();
        }

        user.setActivated(true);
        userService.update(user);

        MActivateResponse response = new MActivateResponse();
        response.setSuccess(true);
        return response;
    }

    //ResendActivationEmail
    public void resendActivationEmail(MResendActivationEmailRequest request) throws BaseException {
        String token = request.getToken();

        if (StringUtil.isNullOrEmpty(token)) {
            //throw error
            throw UserException.resendActivationNoToken();
        }

        Optional<User> opt = userService.findByToken(token);

        if (opt.isEmpty()) {
            //throw error
            throw UserException.resendActivationTokenNotFound();
        }

        User user = opt.get();

        if (user.isActivated()) {
            //throw error
            throw UserException.activateAlready();
        }

        user.setToken(SecurityUtil.generateToken());
        user.setTokenExpire(nextXMinute(30));
        user = userService.update(user);

        sendEmail(user);

    }

    //ฟังชั่นหาเวลา
    private Date nextXMinute(int minie) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minie);
        return calendar.getTime();

    }

    //Refresh-Token
    public String refreshToken() throws BaseException {

        //หาว่าใครกำลัง login อยู่
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            //throw error unauthorized
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        Optional<User> optUser = userService.findById(userId);
        if (optUser.isEmpty()) {
            // throw error not found
            throw UserException.notFound();
        }

        User user = optUser.get();
        return tokenService.tokenize(user);
    }

    //Get user profile
    public MUserProfile getMyUserProfile() throws UserException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        Optional<User> optUser = userService.findById(userId);
        if (optUser.isEmpty()) {
            throw UserException.notFound();
        }

        return userMapper.toUserProfile(optUser.get());
    }

    public MUserProfile updateMyUserProfile(MUpdateUserProfileRequest request) throws UserException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        // validate
        if (ObjectUtils.isEmpty(request.getFirstName())) {
            throw UserException.updateFirstNameNull();
        }

        if (ObjectUtils.isEmpty(request.getLastName())) {
            throw UserException.updateLastNameNull();
        }
        User user = userService.updateUser(userId, request.getFirstName(), request.getLastName());

        return userMapper.toUserProfile(user);
    }

    //listUsers
    public List<User> userList() {
        List<User> response = userService.listUsers();

        return response;
    }

    //getUserById
    public MUserProfile getUser(String id) throws BaseException {
        User user = userService.getUser(id);

        return userMapper.toUserProfile(user);
    }

    //UpdateUserById
    public MUserProfile testUpdate(MUpdateUserProfileRequest request, String id) throws UserException {
        Optional<User> opt = userService.findById(id);
        if (opt.isEmpty()) {
            throw UserException.notFound();
        }
        User user = opt.get();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User response = userService.update(user);

        return userMapper.toUserProfile(user);
    }

    //deleteById
    public void deleteById(String id) {
        userService.deleteById(id);

    }
}
