package com.Project.Backend.config.token;

import com.Project.Backend.service.TokenService;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TokenFilter extends GenericFilterBean {

    private final TokenService tokenService;

    public TokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //แปลง servletRequest ให้เป็น HttpServletRequest
        String authorization = request.getHeader("Authorization"); // ถ้าหากมี header ที่ขึ้นต้นว่า Authorization ก็จะทำงานต้่อ

        if (ObjectUtils.isEmpty(authorization)) { // ถ้าไม่ขึ้นต้นด้วย Authorization
            filterChain.doFilter(servletRequest, servletResponse); // ปล่อยผ่าน
            return; // บังคับให้ออก

        }

        if (!authorization.startsWith("Bearer ")) { // ถ้าไม่ขึ้นต้นด้วย Bearer
            filterChain.doFilter(servletRequest, servletResponse);
            return;

        }

        String token = authorization.substring(7); // เอาค่าหลังคำว่า Bearer ตำแหน่งที่ 7 เป็นต้นไป
        DecodedJWT decoded = tokenService.verify(token);

        if (decoded == null) {  // ถ้า verify ไม่ผ่าน
            filterChain.doFilter(servletRequest, servletResponse);
            return;

        }

        // ผ้่านแล้ว get ค่าออกมา
        String principal = decoded.getClaim("principal").asString(); // principal เก็บเป็น userId
        String role = decoded.getClaim("role").asString();

        //หา role user ที่กำลัง login อยู่ตอนนี้
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));

        //หาว่าใครกำลัง login อยู่ตอนนี้
        //UsernamePasswordAuthenticationToken ต้องการ(user,password,authorities)
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, "(protected)", authorities);

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

        filterChain.doFilter(servletRequest, servletResponse);

    }
}
