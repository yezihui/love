package com.ty.yjx.love.dao;

import com.ty.yjx.love.model.Photo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/25
 */
public interface PhotoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Photo record);

    int insertSelective(Photo record);

    Photo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Photo record);

    int updateByPrimaryKey(Photo record);

    List<Photo> list(@Param("startDay") String startDay, @Param("endDay") String endDay);
}