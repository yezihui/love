package com.ty.yjx.love.dao;

import com.ty.yjx.love.model.AdminUser;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/25
 */
public interface AdminUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);

    AdminUser selectByUserName(String userName);
}