package com.xlproject.modules.common.config;

import com.xlproject.modules.common.interceptor.JwtInterceptor;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private JwtInterceptor jwtInterceptor;
    @Autowired
    public  void  setJwtInterceptor(JwtInterceptor jwtInterceptor){this.jwtInterceptor=jwtInterceptor;}
    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Value("${web.upload-path}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        // 确保上传目录存在
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            if (created) {
                logger.info("上传目录创建成功: {}", uploadPath);
            } else {
                logger.error("上传目录创建失败: {}", uploadPath);
                throw new RuntimeException("无法创建上传目录: " + uploadPath);
            }
        } else {
            logger.info("上传目录已存在: {}", uploadPath);
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射上传文件访问路径
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + uploadPath + "/");

        // 保留Spring Boot默认的静态资源处理
        // 不需要手动添加classpath资源，Spring Boot会自动处理
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptor)
//                .addPathPatterns("/**") // 拦截所有请求
//                .excludePathPatterns("/login", "/logout"); // 排除登录和登出接口
    }
}
