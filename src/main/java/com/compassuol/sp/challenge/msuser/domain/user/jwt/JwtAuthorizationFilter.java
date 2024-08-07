package com.compassuol.sp.challenge.msuser.domain.user.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.compassuol.sp.challenge.msuser.domain.user.jwt.JwtUtils.JWT_BEARER;
import static com.compassuol.sp.challenge.msuser.domain.user.jwt.JwtUtils.getUsernameFromToken;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService detailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(JwtUtils.JWT_AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(JWT_BEARER)) {
            log.info("JWT Token is null, empty or not starting with 'Bearer'");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(JWT_BEARER.length());

        if (!JwtUtils.isTokenValid(token)) {
            log.warn("JWT Token is invalid or expired");
            filterChain.doFilter(request, response);
            return;
        }

        String username = getUsernameFromToken(token);

        toAuthentication(request, username);

        filterChain.doFilter(request, response);
    }

    private void toAuthentication(HttpServletRequest request, String username) {
        UserDetails userDetails = detailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
