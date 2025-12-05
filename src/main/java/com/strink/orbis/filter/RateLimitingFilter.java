package com.strink.orbis.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitingFilter implements Filter {

    private static final int MAX_REQUESTS_PER_MINUTE = 20;
    private final Map<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientIp = httpRequest.getRemoteAddr();
        requestCounts.putIfAbsent(clientIp, new AtomicInteger(0));
        int currentCount = requestCounts.get(clientIp).incrementAndGet();

        if (currentCount > MAX_REQUESTS_PER_MINUTE) {
            httpResponse.setStatus(429);
            httpResponse.getWriter().write("Too many requests. Please try again later.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
