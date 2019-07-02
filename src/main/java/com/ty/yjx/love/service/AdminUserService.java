package com.ty.yjx.love.service;

import com.ty.yjx.love.dao.AdminUserMapper;
import com.ty.yjx.love.dto.admin.LoginDto;
import com.ty.yjx.love.model.AdminUser;
import com.ty.yjx.love.util.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/26
 */
@Service
public class AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    public String selectByUserName(LoginDto loginDto) throws Exception {
        String passwordMd5Str = Md5Utils.md5(loginDto.getPassword());
        AdminUser adminUser = adminUserMapper.selectByUserName(loginDto.getUserName());
        if (adminUser == null) {
            return null;
        }
        if (passwordMd5Str.equals(adminUser.getPassword())) {
            return adminUser.getNickname();
        }
        return null;
    }
}
