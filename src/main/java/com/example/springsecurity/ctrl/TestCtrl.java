package com.example.springsecurity.ctrl;

import com.example.springsecurity.springsecurity.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/test")
public class TestCtrl {

    @GetMapping("aaa")
    public String login(){
        return "sucess.........";
    }

    @PostMapping("parseJWT")
    public String login(String jwt){
        Claims Claims = JwtUtils.parseJWT(jwt);
        return Claims.getId();
    }
}
