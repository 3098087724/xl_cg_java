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
import java.util.Arrays;
import java.util.List;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    // 需要ADMIN权限的接口
    private static final List<String> ADMIN_REQUIRED_PATHS = Arrays.asList(
        "/user/info", "/user/roleStatus", "/user/pwd"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求路径
        String requestURI = request.getRequestURI();

        // 放行登录、登出等公开接口
        if (requestURI.equals("/user/login") || requestURI.equals("/user/logout")) {
            return true;
        }
        // 获取并验证token
        String token = extractToken(request);
        if (token == null) {
            sendErrorResponse(response, "未授权访问，请先登录", 401);
            return false;
        }

        try {
            // 解析token获取用户信息
            String username = jwtUtil.extractUsername(token);
            Long userId = jwtUtil.extractUserId(token);
            String role = jwtUtil.extractRole(token);

            // 验证token是否有效
            if (username == null || !jwtUtil.validateToken(token, username)) {
                sendErrorResponse(response, "登录已过期，请重新登录", 401);
                return false;
            }

            // 检查权限
            if (isAdminRequiredPath(requestURI) && !"ADMIN".equals(role)) {
                sendErrorResponse(response, "无权限操作", 403);
                return false;
            }

            // 将用户信息存入request中
            request.setAttribute("username", username);
            request.setAttribute("userId", userId);
            request.setAttribute("role", role);
            return true;
            
        } catch (Exception e) {
            sendErrorResponse(response, "登录已过期，请重新登录", 401);
            return false;
        }
    }

    /**
     * 提取token
     */
    private String extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
    
    /**
     * 检查路径是否需要ADMIN权限
     */
    private boolean isAdminRequiredPath(String requestURI) {
        return ADMIN_REQUIRED_PATHS.stream().anyMatch(requestURI::startsWith);
    }

    /**
     * 发送错误响应
     */
    private void sendErrorResponse(HttpServletResponse response, String message, int statusCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(statusCode);

        R<?> errorResponse = R.ERROR(statusCode, message);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
