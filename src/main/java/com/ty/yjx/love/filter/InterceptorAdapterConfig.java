package com.ty.yjx.love.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/26
 */
@Configuration
public class InterceptorAdapterConfig extends WebMvcConfigurerAdapter  {

    @Autowired
    private AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor).addPathPatterns("/**").excludePathPatterns("/admin/login", "/wechat/**");
    }


}
