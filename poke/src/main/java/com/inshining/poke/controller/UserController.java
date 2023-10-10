package com.inshining.poke.controller;

import com.inshining.poke.domain.dto.SignUpRequest;
import com.inshining.poke.domain.dto.SignUpResponse;
import com.inshining.poke.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService service;

    @PostMapping("/sign-up")
    public SignUpResponse signUp(@RequestBody SignUpRequest request){
        return service.registerUser(request);
    }
}
