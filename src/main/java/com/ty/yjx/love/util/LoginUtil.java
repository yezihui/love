package com.ty.yjx.love.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/26
 */
public class LoginUtil {

    private static final String LOGIN_SESSION_ADMIN_STATUS_KEY = "love-admin-";
    private static final String LOGIN_SESSION_APP_STATUS_KEY = "love-app-";

    /**
     * 设置后台管理登录session 值为用户名
     *
     * @param request  req请求
     * @param username 用户名
     */
    public static void setAdminLoginStatus(HttpServletRequest request, String username) {
        request.getSession().setAttribute(LOGIN_SESSION_ADMIN_STATUS_KEY, username);
    }

    /**
     * 设置app登录session 值为用户名
     *
     * @param request  req请求
     * @param username 用户名
     */
    public static void setAppLoginStatus(HttpServletRequest request, String username) {
        request.getSession().setAttribute(LOGIN_SESSION_APP_STATUS_KEY, username);
    }

    /**
     * 获取后台管理登录session 值为用户名
     *
     * @param request req请求
     * @return session中的用户名
     */
    public static String getAdminLoginStatus(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(LOGIN_SESSION_ADMIN_STATUS_KEY);
    }

    /**
     * 获取APP登录session 值为用户名
     *
     * @param request req请求
     * @return session中的用户名
     */
    public static String getAppLoginStatus(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(LOGIN_SESSION_APP_STATUS_KEY);
    }

    /**
     * 清除session中登录session
     *
     * @param request req请求
     */
    public static void clearStatus(HttpServletRequest request) {
        request.getSession().removeAttribute(LOGIN_SESSION_ADMIN_STATUS_KEY);
    }
}
