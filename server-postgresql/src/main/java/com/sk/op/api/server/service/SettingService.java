package com.sk.op.api.server.service;

import com.sk.op.api.common.exception.StandardResponseException;
import com.sk.op.api.server.auth.AdminDetails;
import com.sk.op.api.server.dto.ChangeDetailDto;
import com.sk.op.api.server.dto.ChangePasswordDto;
import com.sk.op.api.server.repository.AdminRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Setter(onMethod_ = @Autowired)
public class SettingService {

    private PasswordEncoder passwordEncoder;

    private AdminRepository adminRepository;

    @Transactional
    public void changeDetail(ChangeDetailDto dto, AdminDetails adminDetails) {

        adminRepository.updateDetail(adminDetails.getId(), dto.getNickname(), dto.getAvatar(), dto.getEmail(), dto.getMobile());

        adminDetails.setNickname(dto.getNickname());
        adminDetails.setAvatar(dto.getAvatar());
        adminDetails.setEmail(dto.getEmail());
        adminDetails.setMobile(dto.getMobile());
    }

    @Transactional
    public void changePassword(ChangePasswordDto dto, AdminDetails adminDetails) throws Exception {
        if (!passwordEncoder.matches(dto.getOldPassword(), adminDetails.getPassword())) {
            throw new StandardResponseException("旧密码错误");
        }

        String newPassword = passwordEncoder.encode(dto.getNewPassword());

        adminRepository.updatePassword(adminDetails.getId(), newPassword);

        adminDetails.setPassword(newPassword);
    }
}
