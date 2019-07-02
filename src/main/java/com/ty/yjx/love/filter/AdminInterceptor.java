package com.ty.yjx.love.filter;

import com.ty.yjx.love.util.LoginUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/26
 */
@Component
public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (LoginUtil.getAdminLoginStatus(request) == null) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write("{\"ret\": -2,\"msg\": \"未登录\"}");
            return false;
        }
        return super.preHandle(request, response, handler);
    }

}
