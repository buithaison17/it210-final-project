package com.example.it210finalproject.config;

import com.example.it210finalproject.enums.Role;
import com.example.it210finalproject.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        // Lấy người dùng từ session
        User user = (User) request.getSession().getAttribute("user");

        // Nếu đã đăng nhập không thể quay lại login và register
        if (user != null && (uri.startsWith("/login") || uri.startsWith("/register"))) {
            switch (user.getRole()) {
                case ADMIN -> response.sendRedirect("/admin");
                case STAFF -> response.sendRedirect("/staff");
                case PASSENGER -> response.sendRedirect("/");
            }
            return false;
        }

        // Cho phép truy cập vào login và register nếu chưa đăng nhập
        if (uri.startsWith("/login") || uri.startsWith("/register")) {
            return true;
        }


        // Chưa đăng nhập chuyển về trang đăng nhập
        if (user == null) {
            response.sendRedirect("/login");
            return false;
        }

        // Bảo vệ route admin
        if (uri.startsWith("/admin") && user.getRole() != Role.ADMIN) {
            response.sendRedirect("/login");
            return false;
        }

        // Bảo vệ route staff
        if (uri.startsWith("/staff") && user.getRole() != Role.STAFF) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}
