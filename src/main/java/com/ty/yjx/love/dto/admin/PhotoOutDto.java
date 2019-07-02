package com.ty.yjx.love.dto.admin;

import com.ty.yjx.love.model.Photo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/28
 */
@Data
public class PhotoOutDto {

    private String groupDate;

    private List<Photo> list;
}
