package com.Project.Backend.api;

import com.Project.Backend.business.UserBusiness;
import com.Project.Backend.entity.User;
import com.Project.Backend.exception.BaseException;
import com.Project.Backend.exception.UserException;
import com.Project.Backend.model.*;
import com.Project.Backend.model.MActivateRequest;
import com.Project.Backend.model.MActivateResponse;
import com.Project.Backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserApi {

    private final UserBusiness userBusiness;

    private final UserRepository userRepository;

    public UserApi(UserBusiness userBusiness, UserRepository repository) {
        this.userBusiness = userBusiness;
        this.userRepository = repository;
    }

    @PostMapping("/register")
    public ResponseEntity<MRegisterResponse> register(@RequestBody MRegisterRequest request) throws BaseException {
        MRegisterResponse response = userBusiness.register(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<MLoginResponse> login(@RequestBody MLoginRequest request) throws BaseException {
        MLoginResponse response = userBusiness.login(request);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/activate")
    public ResponseEntity<MActivateResponse> activate(@RequestBody MActivateRequest request) throws BaseException {
        MActivateResponse response = userBusiness.activate(request);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/resend-activation-email")
    public ResponseEntity<Void> resendActivationEmail(@RequestBody MResendActivationEmailRequest request) throws BaseException {
        userBusiness.resendActivationEmail(request);

        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken() throws BaseException {
        String response = userBusiness.refreshToken();

        return ResponseEntity.ok(response);

    }

    @GetMapping("/profile")
    public ResponseEntity<MUserProfile> getMyUserProfile() throws BaseException {
        MUserProfile response = userBusiness.getMyUserProfile();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile")
    public ResponseEntity<MUserProfile> updateMyUserProfile(@RequestBody MUpdateUserProfileRequest request) throws UserException {
        MUserProfile response = userBusiness.updateMyUserProfile(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> userList() {
        List<User> response = userBusiness.userList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<MUserProfile> getUserById(@PathVariable(value = "id") String id) throws BaseException {
        MUserProfile response = userBusiness.getUser(id);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/users/edit/{id}")
    public ResponseEntity<MUserProfile> testUpdateById(@PathVariable(value = "id") String id, @RequestBody MUpdateUserProfileRequest request) throws UserException {
        MUserProfile response = userBusiness.testUpdate(request, id);

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(value = "id") String id) {
        userBusiness.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).build();

    }


}
