package com.ty.yjx.love.controller;

import com.ty.yjx.love.dto.admin.LoginDto;
import com.ty.yjx.love.dto.common.BaseResponse;
import com.ty.yjx.love.dto.common.ResponseGenerator;
import com.ty.yjx.love.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/25
 */
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminUserService adminUserService;

    @PostMapping("/login")
    @ResponseBody
    public BaseResponse login(@RequestBody LoginDto loginDto) {
        try {
            String retStr = adminUserService.selectByUserName(loginDto);

            return ResponseGenerator.genSuccessResult(retStr);
        } catch (Exception e) {
            log.info("d");
            return ResponseGenerator.genFailResult("sd");
        }
    }
}
