package com.xlproject.modules.util.encoder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig  {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        // 强度因子：4-31，推荐10-12
        int strength = 12;
        return new BCryptPasswordEncoder(strength);
    }
}
