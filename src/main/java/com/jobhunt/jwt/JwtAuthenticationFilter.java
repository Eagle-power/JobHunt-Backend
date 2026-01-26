//package com.jobhunt.jwt;
//
//import java.io.IOException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import com.jobhunt.utility.Utilities;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter{
//
//    private final Utilities utilities; 
//	
//	@Autowired
//	private jwtHelper jwtHelper;
//	
//	@Autowired
//	private UserDetailsService userDetailsService;
//
//    JwtAuthenticationFilter(Utilities utilities) {
//        this.utilities = utilities;
//    }
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		String requestHeader = request.getHeader("Authorization");
//		String username = null;
//		String token = null;
//		
//		if(requestHeader !=  null && requestHeader.startsWith("Bearer ")) {
//			token  = requestHeader.substring(7);
//			try {
//				username = this.jwtHelper.getUsernameFromToken(token);
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (ExpiredJwtException e) {
//				e.printStackTrace();
//			} catch (MalformedJwtException e) {
//				e.printStackTrace();
//			} catch (Exception e)  {
//				e.printStackTrace();
//			}
//		} 
//		
//		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
//			UserDetails userDetails  = this.userDetailsService.loadUserByUsername(username);
//			Boolean validateToken = this.jwtHelper.validateToken(token, userDetails.getUsername());
//			if(validateToken) {
//				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				SecurityContextHolder.getContext().setAuthentication(authentication);
//			}
//		}
//		
//		filterChain.doFilter(request, response);
//		
//	}
//	
//}




package com.jobhunt.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private jwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // ✅ Skip preflight requests
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        // ✅ Correct Bearer check
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
            try {
                username = jwtHelper.getUsernameFromToken(token);
            } catch (ExpiredJwtException e) {
                System.out.println("JWT expired");
            } catch (MalformedJwtException e) {
                System.out.println("Invalid JWT");
            } catch (Exception e) {
                System.out.println("JWT error: " + e.getMessage());
            }
        }

        // ✅ Authenticate user
        if (username != null &&
            SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            if (jwtHelper.validateToken(token, userDetails.getUsername())) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}

