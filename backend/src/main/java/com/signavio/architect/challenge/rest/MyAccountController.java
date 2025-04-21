package com.signavio.architect.challenge.rest;

import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/my-account")
public class MyAccountController {

    @GetMapping
    public ResponseEntity<UserInfoDto> getMyInfo() {
        final UserInfoDto userInfo = new UserInfoDto();
        userInfo.setName("Test User");
        return ResponseEntity.of(Optional.of(userInfo));
    }


}
