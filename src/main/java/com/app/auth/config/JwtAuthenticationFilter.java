package com.app.auth.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.auth.services.UserDetailsServiceImpl;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsServiceImpl userDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		
		final String header = request.getHeader("Authorization");
		String jwt = null;
		String username = null;
		
		if(header!=null && header.startsWith("Bearer ")) {
			jwt = header.substring(7);
			username = jwtUtil.getUsernameFromToken(jwt);
		}
//		SecurityContextHolder.getContext().getAuthentication() == null
//		→ means "no user is currently authenticated for this request". It's a common guard 
//		clause to prevent re-authenticating or interfering with existing security context.
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				var userDetails = userDetailService.loadUserByUsername(username);
				if(jwtUtil.validateToken(jwt)) {
					var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
		}
		filterChain.doFilter(request, response);
		
	}

}
