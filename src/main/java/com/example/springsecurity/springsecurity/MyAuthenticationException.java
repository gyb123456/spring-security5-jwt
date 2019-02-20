package com.example.springsecurity.springsecurity;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Spring security其他异常处理类,比如请求路径不存在等，
 * 如果不配置此类，则Spring security默认会跳转到登录页面
 * GYB
 * 20190220
 */
@Component
public class MyAuthenticationException implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        System.out.println("AuthenticationEntryPoint检测到异常:"+e);
        httpServletResponse.setStatus(200);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = httpServletResponse.getWriter();
        httpServletResponse.getWriter().write("AuthenticationEntryPoint检测到异常:"+e);
        printWriter.flush();
    }
}
