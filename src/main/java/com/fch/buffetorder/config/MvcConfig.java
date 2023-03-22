package com.fch.buffetorder.config;

import com.fch.buffetorder.interceptor.LoginInterceptor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @program: BuffetOrder
 * @description: 资源映射
 * @CreatedBy: fch
 * @create: 2022-10-15 21:12
 **/
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    @SneakyThrows
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("file:" + getPath());
    }


    private String getPath() throws FileNotFoundException {
        File path;
        path = new File(ResourceUtils.getURL("classpath:").getPath());
        String pathStr = path.getAbsolutePath().replace("\\target\\classes", "");
        return pathStr + "\\src\\main\\resources\\static\\img\\";
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/Login/**", "/Order/**", "/User/**")
                .excludePathPatterns("/Food/**", "/User/RegUser", "/Food/LoginUser");
    }
}
