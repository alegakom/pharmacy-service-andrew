package org.pharmacy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pharmacy.dto.UserInfoDto;
import org.pharmacy.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserControllerImpl implements UserController{

    private final UserService userService;

    @Override
    public UserInfoDto getUserInfo(String inn) {
        log.info("Received request to get user info for inn: {}", inn);
        return userService.getUserInfo(inn);
    }
}
