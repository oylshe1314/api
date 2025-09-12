package com.sk.op.api.server.auth;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.function.Supplier;

public class RoleAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext context) {
        String reqPath = context.getRequest().getServletPath();
        Authentication authentication = authenticationSupplier.get();
        if (authentication.isAuthenticated()) {
            if (pathMatcher.match("/common/**", reqPath)) {
                return new AuthorizationDecision(true);
            }

            for (GrantedAuthority grantedAuthority : ((AdminAuthToken) authentication).getAuthorities()) {
                if (pathMatcher.match(grantedAuthority.getAuthority(), reqPath)) {
                    return new AuthorizationDecision(true);
                } else {
                    return new AuthorizationDecision(false);
                }
            }
        }

        return new AuthorizationDecision(false);
    }
}
