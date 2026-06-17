package org.pharmacy.controller;

import org.pharmacy.dto.UserInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/user")
public interface UserController {

    @GetMapping("/info/{inn}")
    UserInfoDto getUserInfo(@PathVariable String inn);
}
