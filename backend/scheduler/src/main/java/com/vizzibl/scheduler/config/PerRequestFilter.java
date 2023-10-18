package com.vizzibl.scheduler.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@Component
public class PerRequestFilter extends OncePerRequestFilter {

    private final JwtParserUtil jwtParserUtil;

    public PerRequestFilter(JwtParserUtil jwtParserUtil) {
        this.jwtParserUtil = jwtParserUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        filterChain.doFilter(jwtParserUtil.JwtParser(request), response);
    }

}
