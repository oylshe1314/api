package com.sk.op.api.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.op.api.common.dto.ResponseDto;
import com.sk.op.api.common.handler.CustomRestHandler;
import com.sk.op.api.server.auth.AdminDetails;
import com.sk.op.api.server.dto.LoginResultDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AdminLoginHandler extends CustomRestHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    public AdminLoginHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        respond(response, ResponseDto.succeed(new LoginResultDto((AdminDetails) authentication.getPrincipal())));
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        respondException(response, exception);
    }
}
