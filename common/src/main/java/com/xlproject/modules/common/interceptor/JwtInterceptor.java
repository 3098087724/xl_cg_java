package com.xlproject.modules.common.interceptor;

import com.xlproject.modules.common.response.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xlproject.modules.util.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求路径
        String requestURI = request.getRequestURI();

        // 放行登录、登出等公开接口
        if (requestURI.equals("/user/login") || requestURI.equals("user/logout")) {
            return true;
        }

        // 从请求头中获取token
        String token = request.getHeader("Authorization");

        // 验证token
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // 去掉"Bearer "前缀

            try {
                // 解析token获取用户名
                String username = jwtUtil.extractUsername(token);

                // 验证token是否有效
                if (username != null && jwtUtil.validateToken(token, username)) {
                    // 将用户名存入request中，供后续使用
                    request.setAttribute("username", username);
                    return true;
                }
            } catch (Exception e) {
                // Token解析失败
                sendErrorResponse(response, "Token无效");
                return false;
            }
        }

        // Token不存在或无效
        sendErrorResponse(response, "未授权访问");
        return false;
    }

    /**
     * 发送错误响应
     */
    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        R<?> errorResponse = R.ERROR(401, message);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
