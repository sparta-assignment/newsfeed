package com.sparta.spartime.web.controller;

import com.sparta.spartime.service.SocialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class SocialController {

    private final SocialService socialService;

    @GetMapping("/login/kakao")
    public void kakaosign(@RequestParam("code") String code) throws IOException {
       socialService.login(code);
    }
}
