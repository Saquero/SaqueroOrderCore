package com.saquero.ordercore.infrastructure.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Reads identity headers forwarded by SaqueroGateway and pushes them
 * into MDC so every log line in the request carries full context:
 * userId, userPlan, userRole, tenantId.
 *
 * Headers are set by ClaimsForwardingMiddleware in SaqueroGateway
 * after JWT validation — this service trusts them at the perimeter.
 */
@Component
@Order(2)
public class GatewayContextFilter extends OncePerRequestFilter {

    private static final String HEADER_USER_ID    = "X-User-Id";
    private static final String HEADER_USER_EMAIL = "X-User-Email";
    private static final String HEADER_USER_ROLE  = "X-User-Role";
    private static final String HEADER_USER_PLAN  = "X-User-Plan";
    private static final String HEADER_TENANT_ID  = "X-Tenant-Id";

    private static final String MDC_USER_ID    = "userId";
    private static final String MDC_USER_EMAIL = "userEmail";
    private static final String MDC_USER_ROLE  = "userRole";
    private static final String MDC_USER_PLAN  = "userPlan";
    private static final String MDC_TENANT_ID  = "tenantId";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            setIfPresent(request, HEADER_USER_ID,    MDC_USER_ID);
            setIfPresent(request, HEADER_USER_EMAIL, MDC_USER_EMAIL);
            setIfPresent(request, HEADER_USER_ROLE,  MDC_USER_ROLE);
            setIfPresent(request, HEADER_USER_PLAN,  MDC_USER_PLAN);
            setIfPresent(request, HEADER_TENANT_ID,  MDC_TENANT_ID);

            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_USER_ID);
            MDC.remove(MDC_USER_EMAIL);
            MDC.remove(MDC_USER_ROLE);
            MDC.remove(MDC_USER_PLAN);
            MDC.remove(MDC_TENANT_ID);
        }
    }

    private void setIfPresent(HttpServletRequest request, String header, String mdcKey) {
        String value = request.getHeader(header);
        if (value != null && !value.isBlank()) {
            MDC.put(mdcKey, value);
        }
    }
}