package com.sk.op.api.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.op.api.common.exception.ResponseStatus;
import com.sk.op.api.common.exception.StandardResponseException;
import com.sk.op.api.common.handler.CustomRestHandler;
import jakarta.servlet.ServletException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AdminAccessDeniedHandler extends CustomRestHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    public AdminAccessDeniedHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public void commence(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        respondException(response, new StandardResponseException(ResponseStatus.NOT_LOGGED));
    }

    @Override
    public void handle(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        respondException(response, new StandardResponseException(ResponseStatus.ACCESS_DENIED));
    }
}
