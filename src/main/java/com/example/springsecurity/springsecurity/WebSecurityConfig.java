package com.example.springsecurity.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    // Spring会自动寻找同样类型的具体类注入，这里就是JwtUserDetailsServiceImpl了
    @Autowired
    MyUserDetailsService userDetailsService;
    //登录成功后调用的方法，如返回自定义jwt
    @Autowired
    MyAuthenticationSuccessHandler authenticationSuccessHandler;
    //登录成功后调用的方法
    @Autowired
    MyAuthenticationFailHandler authenticationFailHandler;
    @Autowired
    MyJwtTokenFilter jwtTokenFilter;
    @Autowired
    MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                // 设置UserDetailsService 获取user对象
                .userDetailsService(this.userDetailsService)
                // 自定义密码验证方法
                .passwordEncoder(new PasswordEncoder() {
                    //这个方法没用
                    @Override
                    public String encode(CharSequence charSequence) {
                        return "";
                    }

                    //自定义密码验证方法,charSequence:用户输入的密码，s:我们查出来的数据库密码
                    @Override
                    public boolean matches(CharSequence charSequence, String s) {
                        String pass = MD5Util.string2MD5(charSequence.toString());
                        System.out.println("用户输入密码:" + charSequence + "与数据库相同？" + s.equals(pass));
                        return s.equals(pass);
                    }
                });

    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                //指定登录的地址，用户登录请求必须是表单格式，application/json方式是接受不到参数的
                //这样写参数可以 http://localhost:8080/user/login?username=admin&password=123456
                        formLogin()
                .loginProcessingUrl("/user/login")
                .failureForwardUrl("/error/spring")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailHandler)
                .and()
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 允许对于网站静态资源的无授权访问
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                // 对于获取token的rest api要允许匿名访问
                .antMatchers("/test/**").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        // 禁用缓存
        httpSecurity.headers().cacheControl();

        // 添加JWT filter
        httpSecurity.addFilterBefore(jwtTokenFilter, LogoutFilter.class)
                // 添加权限不足 filter
                .exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);

    }
}