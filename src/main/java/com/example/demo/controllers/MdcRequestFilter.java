package com.example.demo.controllers;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
public class MdcRequestFilter implements Filter {
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        try {
            // Setup MDC data in the current request thread-context-:
            MDC.put("request-remote-host", servletRequest.getRemoteHost()); //Variable 'mdcData' is referenced in Spring Boot's logging.pattern.level property
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            // ( Important! clean up the map after the request has been processed.
            MDC.clear();
        }
    }

    @Override
    public void destroy() {

    }
}
