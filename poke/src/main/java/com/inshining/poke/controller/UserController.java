package com.inshining.poke.controller;

import com.inshining.poke.domain.dto.SignInRequest;
import com.inshining.poke.domain.dto.SignInResponse;
import com.inshining.poke.domain.dto.SignUpRequest;
import com.inshining.poke.domain.dto.SignUpResponse;
import com.inshining.poke.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/login")
    public SignInResponse signIn(@RequestBody SignInRequest request) {
        return service.signIn(request);
    }

    @GetMapping("/my")
    public String getMyPage(){
        return "success";
    }

}
