package com.kiyotabgangers.resourceserverforblog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KIYOTA, Takeshi
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/status")
    public String status(){
        return "working...";
    }
}
