package com.sk.op.api.server.controller;

import com.sk.op.api.common.dto.ResponseDto;
import com.sk.op.api.server.auth.AdminDetails;
import com.sk.op.api.server.dto.ChangeDetailDto;
import com.sk.op.api.server.dto.ChangePasswordDto;
import com.sk.op.api.server.service.SettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/setting")
@Tag(name = "setting",  description = "设置相关接口")
public class SettingController {

    private final SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @Operation(summary = "修改详细信息")
    @RequestMapping(value = "/change/detail", method = RequestMethod.POST)
    public ResponseDto<?> changeDetail(@RequestBody @Valid ChangeDetailDto dto, Authentication authentication) throws Exception {
        settingService.changeDetail(dto, (AdminDetails) authentication.getPrincipal());
        return ResponseDto.succeed();
    }

    @Operation(summary = "修改密码")
    @RequestMapping(value = "/change/password", method = RequestMethod.POST)
    public ResponseDto<?> changePassword(@RequestBody @Valid ChangePasswordDto dto, Authentication authentication) throws Exception {
        settingService.changePassword(dto, (AdminDetails) authentication.getPrincipal());
        return ResponseDto.succeed();
    }
}
