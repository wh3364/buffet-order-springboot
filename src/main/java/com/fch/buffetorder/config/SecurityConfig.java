package com.fch.buffetorder.config;


import com.fch.buffetorder.filter.JwtAuthenticationFilter;
import com.fch.buffetorder.filter.JwtLoginFilter;
import com.fch.buffetorder.handler.FailureHandler;
import com.fch.buffetorder.handler.SuccessHandler;
import com.fch.buffetorder.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-31 18:01
 **/
@EnableWebSecurity
public class SecurityConfig {

    private final
    AdminService AdminDetailsService;

    private final
    SuccessHandler successHandler;

    private final
    FailureHandler failureHandler;

    private final String[] USER_PATH = {"/Food/**", "/Login/**", "/Order/**", "/User/**"};
    private final String[] PASS_PATH = {"/login", "/logout", "/reg"};

    @Autowired
    public SecurityConfig(AdminService AdminDetailsService, SuccessHandler successHandler, FailureHandler failureHandler) {
        this.AdminDetailsService = AdminDetailsService;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 设置 session 为无状态，因为基于 token 不需要 session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 权限配置
                .authorizeRequests()
                .antMatchers("/Assistant/**").hasAnyRole("admin", "assistant")
                .antMatchers("/Admin/**").hasRole("admin")
                .antMatchers(USER_PATH).permitAll()
                .antMatchers(PASS_PATH).permitAll()
                .and()
                // 登出
                .logout().logoutUrl("/logout")
                .and()
                // 登录
                .formLogin().loginPage("/login").loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/error/error/error")
                .permitAll()
                .and()
                // 开启跨域 cors()
                .cors().configurationSource(corsConfigurationSource())
                .and().csrf().disable()
                .addFilter(jwtLoginFilter(http))
                .addFilter(jwtAuthenticationFilter(http))
                .build();
    }

    @Bean
    JwtLoginFilter jwtLoginFilter(HttpSecurity http) throws Exception {
        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter(authenticationManager(http));
        jwtLoginFilter.setAuthenticationSuccessHandler(successHandler);
        jwtLoginFilter.setAuthenticationFailureHandler(failureHandler);
        return jwtLoginFilter;
    }

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(HttpSecurity http) throws Exception {
        return new JwtAuthenticationFilter(authenticationManager(http));
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                // 忽略静态资源路径
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(AdminDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //spring security 配置跨域访问资源
    private CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*"); //同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
        corsConfiguration.addAllowedHeader("*");  //header，允许哪些header
        corsConfiguration.addAllowedMethod("*");  //允许的请求方法，POST、GET、PUT等
        corsConfiguration.addExposedHeader("token"); //拓展header 浏览器放过response的token
        corsConfiguration.setAllowCredentials(true); //允许浏览器携带cookie
        source.registerCorsConfiguration("/**", corsConfiguration); //配置允许跨域访问的url
        return source;
    }
}
