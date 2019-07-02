package com.ty.yjx.love.service;

import com.ty.yjx.love.dao.PhotoMapper;
import com.ty.yjx.love.model.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/28
 */
@Service
public class PhotoService {

    @Autowired
    private PhotoMapper photoMapper;

    public List<Photo> list(String startDay, String endDay) {
        return photoMapper.list(startDay, endDay);
    }
}
