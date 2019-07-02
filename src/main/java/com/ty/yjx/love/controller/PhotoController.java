package com.ty.yjx.love.controller;

import com.ty.yjx.love.dto.admin.LoginDto;
import com.ty.yjx.love.dto.admin.PhotoDto;
import com.ty.yjx.love.dto.admin.PhotoOutDto;
import com.ty.yjx.love.dto.common.BaseResponse;
import com.ty.yjx.love.dto.common.ResponseGenerator;
import com.ty.yjx.love.model.Photo;
import com.ty.yjx.love.service.PhotoService;
import com.ty.yjx.love.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/28
 */
@Slf4j
@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping("/list")
    public BaseResponse list(@RequestBody PhotoDto photoDto) {
        try {
            if (photoDto.getStartTime() == null) {
                photoDto.setStartTime(DateUtil.addDay(new Date(), -5));
            }
            if (photoDto.getEndTime() == null) {
                photoDto.setEndTime(new Date());
            }
            List<Photo> list = photoService.list(DateUtil.dateToStr(photoDto.getStartTime(), DateUtil.YYYY_MM_DD),
                    DateUtil.dateToStr(photoDto.getEndTime(), DateUtil.YYYY_MM_DD));
            Map<String, List<Photo>> groupList = list.stream().collect(Collectors.groupingBy(Photo::getGroupDate));
            PhotoOutDto outDto;
            List<PhotoOutDto> resultList = new ArrayList<>(groupList.size());
            for (Map.Entry<String, List<Photo>> entry : groupList.entrySet()) {
                outDto = new PhotoOutDto();
                outDto.setGroupDate(entry.getKey());
                outDto.setList(entry.getValue());
                resultList.add(outDto);
            }
            resultList.sort(Comparator.comparing(PhotoOutDto::getGroupDate).reversed());
            return ResponseGenerator.genSuccessResult(resultList);
        } catch (Exception e) {
            log.info("d");
            return ResponseGenerator.genFailResult("sd");
        }
    }
}
