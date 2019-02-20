package com.example.springsecurity.ctrl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/role")
public class RoletestCtrl {

    @GetMapping("aaa")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String aaa(){
        return "sucess.........aaa";
    }

    @GetMapping("bbb")
    public String bbb(){
        return "sucess.........bbb";
    }

    @GetMapping("ccc")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String ccc(){
        return "sucess.........ccc";
    }
}
