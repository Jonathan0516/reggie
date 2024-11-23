package com.itheima.reggie.config;

import com.itheima.reggie.filter.LoginFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 设置静态资源映射
     * @param registry
     */
    @Autowired
    private LoginFilter loginFilter;
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射...");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }


    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginFilter)
                .addPathPatterns("/backend/**") // 拦截所有静态资源请求
                .excludePathPatterns(
                        "/backend/page/login/login.html", // 排除登录页面
                        "/employee/login",               // 排除登录接口
                        "/backend/**/*.css",             // 排除 CSS 文件
                        "/backend/**/*.js",              // 排除 JS 文件
                        "/backend/**/*.png",             // 排除图片文件
                        "/backend/**/*.jpg",
                        "/backend/**/*.jpeg",
                        "/backend/**/*.gif"
                );
    }
}
