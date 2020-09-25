package com.BillCalculator.auth;

import com.BillCalculator.entity.UserRoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenManager tokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = httpServletRequest.getHeader("Authorization");
        String username = null;
        String token = null;

        if (authHeader != null && authHeader.contains("Bearer")) {
            token = authHeader.substring(7);
            try {
                username = tokenManager.getUserNameFromToken(token);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        if (username != null && token != null && SecurityContextHolder // bu kullanici sisteme hic girmemisse // bu kullanici daha once bu token ile login olmamissa
                .getContext().getAuthentication() == null) {
            if (tokenManager.tokenValidate(token)) {
                final SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(UserRoleEnum.USER.name());
                UsernamePasswordAuthenticationToken usernamePassordToken =
                        new UsernamePasswordAuthenticationToken(username, null, Collections.singleton(simpleGrantedAuthority));
                usernamePassordToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePassordToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
