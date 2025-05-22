// package com.DariM.darim.utils;

// import java.io.IOException;
// import java.util.concurrent.TimeUnit;
// import jakarta.servlet.http.Cookie;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;
// import com.DariM.darim.service.MyUserDetailsService;
// import io.jsonwebtoken.ExpiredJwtException;
// import io.jsonwebtoken.Claims;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @Component
// public class JwtFilter extends OncePerRequestFilter {

//     @Autowired
//     private JwtUtil jwtUtil;
    
//     @Autowired
//     private MyUserDetailsService userDetailsService;

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//             throws ServletException, IOException {
        
//         final String requestURI = request.getRequestURI();
//         // ...existing code...
// String path = request.getRequestURI();
// if (path.equals("/api/auth/") ||
//     path.equals("/api/auth/register") || 
//     path.equals("/api/auth/login") ||
//     path.equals("/api/auth/forgot-password") ||
//     path.equals("/api/auth/reset-password")) {
//     chain.doFilter(request, response);
//     return;
// }
// // ...existing code...
//         // Skip filter for public endpoints
//         if (shouldSkipAuthentication(requestURI)) {
//             chain.doFilter(request, response);
//             return;
//         }

//         try {
//             String token = resolveToken(request);
            
//             if (token == null) {
//                 sendError(response, "Missing token - please login", HttpServletResponse.SC_UNAUTHORIZED);
//                 return;
//             }

//             String username = jwtUtil.extractUsername(token);
            
//             if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                 UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                 if (jwtUtil.validateToken(token, userDetails)) {
//                     UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
//                         userDetails, null, userDetails.getAuthorities());
//                     SecurityContextHolder.getContext().setAuthentication(auth);
//                 }
//             }
            
//             chain.doFilter(request, response);
            
//         } catch (ExpiredJwtException ex) {
//             // Ajout du header pour indiquer que le token peut être rafraîchi
//             if (canRefreshToken(ex.getClaims())) {
//                 response.setHeader("X-Token-Refreshable", "true");
//             }
//             response.setHeader("X-Token-Expired", "true");
//             response.setHeader("X-Refresh-Token-URL", "/auth/refresh-token");
//             sendError(response, "Token expired - please refresh", HttpServletResponse.SC_UNAUTHORIZED);
//         } catch (Exception ex) {
//             sendError(response, "Invalid token - please login again", HttpServletResponse.SC_FORBIDDEN);
//         }
//     }

//     private boolean shouldSkipAuthentication(String requestURI) {
//         return requestURI.startsWith("/auth/") || 
//                requestURI.startsWith("/api/auth/refresh-token") ||
//                requestURI.startsWith("/public/") || 
//                requestURI.equals("/error") ||
//                requestURI.startsWith("/login") ||
//                requestURI.startsWith("/register") ||
//                requestURI.startsWith("/forgot-password") ||
//                requestURI.startsWith("/reset-password") ||
//                requestURI.startsWith("/swagger-ui") ||
//                requestURI.startsWith("/v3/api-docs") ||
//                requestURI.startsWith("/css/") ||
//                requestURI.startsWith("/js/");
//     }

//     private String resolveToken(HttpServletRequest request) {
//         // Check Authorization header (Bearer token)
//         String header = request.getHeader("Authorization");
//         if (header != null && header.startsWith("Bearer ")) {
//             return header.substring(7);
//         }
        
//         // Check cookies
//         Cookie[] cookies = request.getCookies();
//         if (cookies != null) {
//             for (Cookie cookie : cookies) {
//                 if ("Authorization".equals(cookie.getName())) {
//                     return cookie.getValue();
//                 }
//             }
//         }
//         return null;
//     }

//     private void sendError(HttpServletResponse response, String message, int status) throws IOException {
//         response.setContentType("application/json");
//         response.setStatus(status);
//         response.getWriter().write(String.format(
//             "{\"error\": \"%s\", \"timestamp\": %d}", 
//             message, 
//             System.currentTimeMillis()
//         ));
//     }

//     private boolean canRefreshToken(Claims claims) {
//         long tokenAgeMs = System.currentTimeMillis() - claims.getIssuedAt().getTime();
//         return tokenAgeMs < TimeUnit.DAYS.toMillis(7); // 7 jours max pour rafraîchir
//     }
// }

package com.DariM.darim.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.DariM.darim.service.MyUserDetailsService;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        
        String path = request.getRequestURI();
        
        // Skip JWT filter for non-auth endpoints
        if (!path.startsWith("/api/auth/")) {
            chain.doFilter(request, response);
            return;
        }

        // Skip JWT filter for login/register endpoints
        if (path.equals("/api/auth/login") || path.equals("/api/auth/register")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String token = extractToken(request);
            
            if (token == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing JWT token");
                return;
            }

            String username = jwtUtil.extractUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(token, userDetails)) {
                    var authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            
            chain.doFilter(request, response);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid JWT token");
        }
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return !path.startsWith("/api/auth/");
    }
}

