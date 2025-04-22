package com.signavio.architect.challenge.rest;

import com.signavio.architect.challenge.repository.entities.UserEntity;
import com.signavio.architect.challenge.services.UserService;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/my-account")
public class MyAccountController {

    private final UserService userService;

    public MyAccountController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserInfoDto> getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
        if (!StringUtils.hasText(userDetails.getUsername())) {
            return ResponseEntity.notFound().build();
        }
        final Optional<UserEntity> userByUsernameOpt = this.userService.findUserByUsername(userDetails.getUsername());
        if (userByUsernameOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        final UserInfoDto userInfo = new UserInfoDto();
        userInfo.setFirstName(userByUsernameOpt.get().getFirstName());
        userInfo.setLastName(userByUsernameOpt.get().getLastName());
        return ResponseEntity.of(Optional.of(userInfo));
    }


}
