package com.sk.op.api.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.op.api.server.auth.AdminLoginProvider;
import com.sk.op.api.server.auth.RoleAuthorizationManager;
import com.sk.op.api.server.filter.AdminLoginFilter;
import com.sk.op.api.server.handler.AdminAccessDeniedHandler;
import com.sk.op.api.server.handler.AdminLoginHandler;
import com.sk.op.api.server.handler.AdminLogoutHandler;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;

@EnableWebSecurity
@SpringBootConfiguration
@Setter(onMethod_ = @Autowired)
public class AdminSecurityConfiguration {

    private ObjectMapper objectMapper;
    private AdminLoginProvider adminLoginProvider;
    private AdminLoginHandler adminLoginHandler;
    private AdminLogoutHandler adminLogoutHandler;
    private AdminAccessDeniedHandler adminAccessDeniedHandler;
    private SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors(CorsConfigurer::disable);
        httpSecurity.csrf(CsrfConfigurer::disable);
//        httpSecurity.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
        httpSecurity.securityContext(securityContext -> securityContext.securityContextRepository(securityContextRepository));
        httpSecurity.httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(adminAccessDeniedHandler));
        httpSecurity.exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(adminAccessDeniedHandler));
        httpSecurity.authorizeHttpRequests(requests -> requests.requestMatchers("/doc/**", "/v3/api-docs/**", "/error/**").permitAll().anyRequest().access(new RoleAuthorizationManager()));
        httpSecurity.logout(logout -> logout.logoutUrl("/auth/logout").addLogoutHandler(adminLogoutHandler).logoutSuccessHandler(adminLogoutHandler));

        httpSecurity.addFilterAt(adminLoginFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    private AdminLoginFilter adminLoginFilter() {
        AdminLoginFilter adminLoginFilter = new AdminLoginFilter("/auth/login", objectMapper);
        adminLoginFilter.setAuthenticationManager(new ProviderManager(adminLoginProvider));
        adminLoginFilter.setAuthenticationSuccessHandler(adminLoginHandler);
        adminLoginFilter.setAuthenticationFailureHandler(adminLoginHandler);
        adminLoginFilter.setSecurityContextRepository(securityContextRepository);
        return adminLoginFilter;
    }
}
