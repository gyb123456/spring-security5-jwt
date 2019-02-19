package com.example.springsecurity.springsecurity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 根据用户名获取数据库该用户信息，spring security在登录时自动调用
 * WebSecurityConfigurerAdapter会拿这里的User里的password与用户输入的对比验证
 * GYB
 * 20190219
 */
@Service
public  class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        GrantedAuthority au = new SimpleGrantedAuthority("ROLE_USER");
        list.add(au);
        //123456 自定义MD5加密后=e10adc3949ba59abbe56e057f20f883e
        JwtUser JwtUser = new JwtUser(s,"e10adc3949ba59abbe56e057f20f883e",list);
        if (JwtUser == null) {
            throw new UsernameNotFoundException(String.format("No user found with username."));
        }
        System.out.println(JwtUser);
        return JwtUser;
    }
}
