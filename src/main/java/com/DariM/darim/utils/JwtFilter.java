// // package com.DariM.darim.utils;

// // import java.io.IOException;
// // import java.util.concurrent.TimeUnit;
// // import jakarta.servlet.http.Cookie;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// // import org.springframework.security.core.context.SecurityContextHolder;
// // import org.springframework.security.core.userdetails.UserDetails;
// // import org.springframework.stereotype.Component;
// // import org.springframework.web.filter.OncePerRequestFilter;
// // import com.DariM.darim.service.MyUserDetailsService;
// // import io.jsonwebtoken.ExpiredJwtException;
// // import io.jsonwebtoken.Claims;
// // import jakarta.servlet.FilterChain;
// // import jakarta.servlet.ServletException;
// // import jakarta.servlet.http.HttpServletRequest;
// // import jakarta.servlet.http.HttpServletResponse;

// // @Component
// // public class JwtFilter extends OncePerRequestFilter {

// //     @Autowired
// //     private JwtUtil jwtUtil;
    
// //     @Autowired
// //     private MyUserDetailsService userDetailsService;

// //     @Override
// //     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
// //             throws ServletException, IOException {
        
// //         final String requestURI = request.getRequestURI();
        
// //         // Skip filter for public endpoints
// //         if (shouldSkipAuthentication(requestURI)) {
// //             chain.doFilter(request, response);
// //             return;
// //         }

// //         try {
// //             String token = resolveToken(request);
            
// //             if (token == null) {
// //                 sendError(response, "Missing token - please login", HttpServletResponse.SC_UNAUTHORIZED);
// //                 return;
// //             }

// //             String username = jwtUtil.extractUsername(token);
            
// //             if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
// //                 UserDetails userDetails = userDetailsService.loadUserByUsername(username);
// //                 if (jwtUtil.validateToken(token, userDetails)) {
// //                     UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
// //                         userDetails, null, userDetails.getAuthorities());
// //                     SecurityContextHolder.getContext().setAuthentication(auth);
// //                 }
// //             }
            
// //             chain.doFilter(request, response);
            
// //         } catch (ExpiredJwtException ex) {
// //             if (canRefreshToken(ex.getClaims())) {
// //                 response.setHeader("X-Token-Refreshable", "true");
// //             }
// //             response.setHeader("X-Token-Expired", "true");
// //             response.setHeader("X-Refresh-Token-URL", "/auth/refresh-token");
// //             sendError(response, "Token expired - please refresh", HttpServletResponse.SC_UNAUTHORIZED);
// //         } catch (Exception ex) {
// //             sendError(response, "Invalid token - please login again", HttpServletResponse.SC_FORBIDDEN);
// //         }
// //     }

// //     private boolean shouldSkipAuthentication(String requestURI) {
// //         return requestURI.startsWith("/api/auth/") || 
// //                requestURI.startsWith("/api/auth/refresh-token") ||
// //                requestURI.startsWith("/public/") || 
// //                requestURI.equals("/error") ||
// //                requestURI.startsWith("/login") ||
// //                requestURI.startsWith("/register") ||
// //                requestURI.startsWith("/forgot-password") ||
// //                requestURI.startsWith("/reset-password") ||
// //                requestURI.startsWith("/swagger-ui") ||
// //                requestURI.startsWith("/v3/api-docs") ||
// //                requestURI.startsWith("/css/") ||
// //                requestURI.startsWith("/js/") ||
// //                // Endpoints de ChambreController
// //            requestURI.equals("/api/chambres") ||  
// //            requestURI.startsWith("/api/chambres/search") ||
// //            requestURI.matches("/api/chambres/\\d+") ||  
// //            requestURI.equals("/api/chambres/user") ||  
// //            requestURI.equals("/api/chambres") ||  
// //            requestURI.matches("/api/chambres/\\d+") ||  
// //            requestURI.equals("/api/chambres/admin/attente") ||
// //            requestURI.matches("/api/chambres/admin/\\d+/valider") ||
// //            requestURI.matches("/api/chambres/admin/\\d+") ||
// //            requestURI.matches("/api/chambres/\\d+/reserver") ||
// //            requestURI.matches("/api/chambres/admin/\\d+/liberer");
// //     }

// //     private String resolveToken(HttpServletRequest request) {
// //         // Check Authorization header (Bearer token)
// //         String header = request.getHeader("Authorization");
// //         if (header != null && header.startsWith("Bearer ")) {
// //             return header.substring(7);
// //         }
        
// //         // Check cookies
// //         Cookie[] cookies = request.getCookies();
// //         if (cookies != null) {
// //             for (Cookie cookie : cookies) {
// //                 if ("Authorization".equals(cookie.getName())) {
// //                     return cookie.getValue();
// //                 }
// //             }
// //         }
// //         return null;
// //     }

// //     private void sendError(HttpServletResponse response, String message, int status) throws IOException {
// //         response.setContentType("application/json");
// //         response.setStatus(status);
// //         response.getWriter().write(String.format(
// //             "{\"error\": \"%s\", \"timestamp\": %d}", 
// //             message, 
// //             System.currentTimeMillis()
// //         ));
// //     }

// //     private boolean canRefreshToken(Claims claims) {
// //         long tokenAgeMs = System.currentTimeMillis() - claims.getIssuedAt().getTime();
// //         return tokenAgeMs < TimeUnit.DAYS.toMillis(7); // 7 jours max pour rafraîchir
// //     }
// // }


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
//         return requestURI.startsWith("/api/auth/") || 
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
//                requestURI.startsWith("/js/") ||
//                // Autoriser tous les endpoints de chambres sans authentification
//                requestURI.startsWith("/api/chambres");
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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain chain)
            throws ServletException, IOException {
        chain.doFilter(request, response);
    }
}