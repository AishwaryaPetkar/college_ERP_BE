package com.app.auth.controllers;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.auth.config.JwtUtil;
import com.app.auth.dto.LoginDTO;
import com.app.auth.dto.UserSignupDTO;
import com.app.auth.entities.RefreshToken;
import com.app.auth.entities.Role;
import com.app.auth.entities.User;
import com.app.auth.repository.RefreshTokenRepository;
import com.app.auth.repository.UserRepository;
import com.app.auth.services.RefreshTokenService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/signup")
	public String signup(@RequestBody @Valid UserSignupDTO dto) {
		if (userRepository.findByUserName(dto.getEmail()).isPresent()) {
			return "User already exists.";
		}

		User user = new User();
		user.setFullName(dto.getFullName());
		user.setUserName(dto.getEmail());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setDOB(dto.getDOB());
		user.setGender(dto.getGender());
		user.setRole(Role.USER); 

		userRepository.save(user);
		return "User registered successfully.";
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid LoginDTO dto) {
	    Optional<User> optionalUser = userRepository.findByUserName(dto.getEmail());
	    if (optionalUser.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(Map.of("message", "Invalid Username", "isAuthenticated", false));
	    }

	    User user = optionalUser.get();
	    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(Map.of("message", "Invalid password", "isAuthenticated", false));
	    }

	    String accessToken = jwtUtil.generateToken(user.getUserName(), 2*60 * 60 * 1000); // 2 min
//	    String refreshToken = jwtUtil.generateRefreshToken(user.getUserName(), 7 * 24 * 60 * 60 * 1000); // 7 days
	    
	    // Save refresh token in DB
	    RefreshToken tokenEntity = new RefreshToken();
	    tokenEntity = refreshTokenService.createRefreshToken(user);
	    String refreshToken = tokenEntity.getToken();

	    ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
	            .httpOnly(true)
	            .secure(false)
	            .path("/")
	            .maxAge(Duration.ofDays(7))
	            .sameSite("Lax")
	            .build();

	    return ResponseEntity.ok()
	            .header(HttpHeaders.SET_COOKIE, cookie.toString())
	            .body(Map.of(
	                    "accessToken", accessToken,
	                    "fullName", user.getFullName(),
	                    "email", user.getUserName(),
	                    "role", user.getRole(),
	                    "isAuthenticated", true,
	                    "user_id", user.getUserId()
	            ));
	}
	
	@GetMapping("/refresh")
	public ResponseEntity<?> refreshToken(
	        @CookieValue(value = "refreshToken", required = false) String refreshTokenCookie) {

	    if (refreshTokenCookie == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(Map.of("message", "No refresh token provided"));
	    }

	    Optional<RefreshToken> optionalToken = refreshTokenService.findByTokenWithUser(refreshTokenCookie);

	    // Check if token exists and is not expired
	    if (optionalToken.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                .body(Map.of("isAuthenticated", false, "message", "Invalid refresh token"));
	    }

	    RefreshToken oldRefreshToken = optionalToken.get();

	    if (refreshTokenService.isExpired(oldRefreshToken)) {
	        refreshTokenService.delete(oldRefreshToken); // Delete expired token
	        return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                .body(Map.of("isAuthenticated", false, "message", "Expired refresh token"));
	    }

	    User user = oldRefreshToken.getUser();

	    // Create new tokens
	    String newAccessToken = jwtUtil.generateToken(user.getUserName(), 5 * 60 * 60 * 1000); // 5 minutes
	    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);

	    refreshTokenService.delete(oldRefreshToken); // Clean up

	    // Set new cookie
	    ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", newRefreshToken.getToken())
	            .httpOnly(true)
	            .secure(false) // change to true in production with HTTPS
	            .path("/")
	            .sameSite("Lax")
	            .maxAge(Duration.ofDays(7))
	            .build();

	    return ResponseEntity.ok()
	            .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
	            .body(Map.of("newAccessToken", newAccessToken));
	}



//	@GetMapping("/refresh")
//	public ResponseEntity<?> refreshToken(@CookieValue(value = "refreshToken", required = false) String token) {
//	    System.out.println("==================Token==========="+token);
//		if (token == null) {
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "No refresh token"));
//	    }
//
//	    Optional<RefreshToken> optionalToken = refreshTokenService.findByToken(token);
//	    System.out.println("Optional Token (refresh)==============="+optionalToken);
//	    if (optionalToken.isEmpty() || refreshTokenService.isExpired(optionalToken.get())) {
//	        optionalToken.ifPresent(refreshTokenService::delete);
//	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("isAuthenticated", false, "message", "Invalid or expired refresh token"));
//	    }
//
//	    RefreshToken refreshToken = optionalToken.get();
//	    User user = refreshToken.getUser();
//
//	    // Generate new tokens
//	    String newAccessToken = jwtUtil.generateToken(user.getUserName(), 5 * 60 * 1000); // 5 min
//	    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);
//
//	    // Delete old token
//	    refreshTokenService.delete(refreshToken);
//
//	    ResponseCookie cookie = ResponseCookie.from("refreshToken", newRefreshToken.getToken())
//	    		.httpOnly(true)
//	            .secure(false) // change to true only when on HTTPS
//	            .path("/")     // or at least "/api"
//	            .sameSite("Lax") // Lax allows top-level navigations and POSTs
//	            .maxAge(Duration.ofDays(7))
//	            .build();
//
//	    return ResponseEntity.ok()
//	            .header(HttpHeaders.SET_COOKIE, cookie.toString())
//	            .body(Map.of("accessToken", newAccessToken));
//	}

	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestBody Map<String, String> body) {
	    String email = body.get("email");
	    User user = userRepository.findByUserName(email)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

	    refreshTokenService.deleteAllUserTokens(user);

	    ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
	            .httpOnly(true)
	            .secure(false)
	            .path("/")
	            .maxAge(0)
	            .sameSite("Lax")
	            .build();

	    return ResponseEntity.ok()
	            .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
	            .body(Map.of("message", "Logged out", "isAuthenticated", false));
	}



//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody @Valid LoginDTO dto) {
//	    Optional<User> optionalUser = userRepository.findByUserName(dto.getEmail());
//
//	    if (optionalUser.isEmpty()) {
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//	                .body(Map.of("message", "Invalid Username", "isAuthenticated", false));
//	    }
//
//	    User user = optionalUser.get();
//
//	    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//	                .body(Map.of("message", "Invalid password", "isAuthenticated", false));
//	    }
//
//	    String accessToken = jwtUtil.generateToken(user.getUserName(), 2 * 60 * 1000);
//	    String refreshTokenStr = UUID.randomUUID().toString();
//
//	    RefreshToken refreshToken = new RefreshToken();
//	    refreshToken.setToken(refreshTokenStr);
//	    refreshToken.setUser(user);
//	    refreshToken.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));
//	    refreshTokenRepository.save(refreshToken);
//
//	    ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshTokenStr)
//	            .httpOnly(true)
//	            .secure(true)
//	            .path("/api/auth/refresh")
//	            .maxAge(Duration.ofDays(7))
//	            .sameSite("Strict")
//	            .build();
//
//	    return ResponseEntity.ok()
//	            .header(HttpHeaders.SET_COOKIE, cookie.toString())
//	            .body(Map.of(
//	                    "fullName", user.getFullName(),
//	                    "email", user.getUserName(),
//	                    "accessToken", accessToken,
//	                    "role", user.getRole(),
//	                    "isAuthenticated", true
//	            ));
//	}
//	
//	@PostMapping("/refresh")
//	public ResponseEntity<?> refreshToken(@CookieValue(name = "refreshToken", required = false) String token) {
//	    if (token == null) {
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "No refresh token"));
//	    }
//
//	    Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByToken(token);
//
//	    if (refreshTokenOptional.isEmpty()) {
//	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Invalid refresh token"));
//	    }
//
//	    RefreshToken refreshToken = refreshTokenOptional.get();
//
//	    if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
//	        refreshTokenRepository.delete(refreshToken);
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Refresh token expired"));
//	    }
//
//	    User user = refreshToken.getUser();
//	    String newAccessToken = jwtUtil.generateToken(user.getUserName(), 5 * 60 * 1000);
//	    String newRefreshTokenStr = UUID.randomUUID().toString();
//
//	    RefreshToken newRefreshToken = new RefreshToken();
//	    newRefreshToken.setToken(newRefreshTokenStr);
//	    newRefreshToken.setUser(user);
//	    newRefreshToken.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));
//
//	    refreshTokenRepository.delete(refreshToken);
//	    refreshTokenRepository.save(newRefreshToken);
//
//	    ResponseCookie cookie = ResponseCookie.from("refreshToken", newRefreshTokenStr)
//	            .httpOnly(true)
//	            .secure(true)
//	            .path("/api/auth/refresh")
//	            .maxAge(Duration.ofDays(7))
//	            .sameSite("Strict")
//	            .build();
//
//	    return ResponseEntity.ok()
//	            .header(HttpHeaders.SET_COOKIE, cookie.toString())
//	            .body(Map.of("accessToken", newAccessToken));
//	}
//	
//	@PostMapping("/logout")
//	public ResponseEntity<?> logout(@CookieValue(name = "refreshToken", required = false) String token) {
//	    if (token != null) {
//	        refreshTokenRepository.deleteByToken(token);
//	    }
//
//	    ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
//	            .httpOnly(true)
//	            .secure(true)
//	            .path("/api/auth/refresh")
//	            .maxAge(0)
//	            .sameSite("Strict")
//	            .build();
//
//	    return ResponseEntity.ok()
//	            .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
//	            .body(Map.of("isAuthenticated", false, "message", "Logged out"));
//	}
}


//✅ Step 1: Update UserRepository
//Add this method to your UserRepository interface:
//
//java
//Copy
//Edit
//Optional<User> findByRefreshToken(String refreshToken);
//✅ Step 2: Update /refresh Endpoint in UserController
//java
//Copy
//Edit
//@PostMapping("/refresh")
//public ResponseEntity<Map<String, Object>> refreshToken(
//        @CookieValue(name = "refreshToken", required = false) String token) {
//
//    if (token == null) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .body(Map.of("error", "No refresh token"));
//    }
//
//    Optional<User> optionalUser = userRepository.findByRefreshToken(token);
//
//    if (optionalUser.isEmpty()) {
//        return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                .body(Map.of("isAuthenticated", false, "message", "Invalid refresh token"));
//    }
//
//    User user = optionalUser.get();
//    String newAccessToken = jwtUtil.generateToken(user.getUserName(), 1000 * 60 * 5);
//    String newRefreshToken = UUID.randomUUID().toString();
//
//    user.setRefreshToken(newRefreshToken);
//    userRepository.save(user);
//
//    ResponseCookie cookie = ResponseCookie.from("refreshToken", newRefreshToken)
//            .httpOnly(true)
//            .secure(true)
//            .path("/api/auth/refresh")
//            .maxAge(Duration.ofDays(7))
//            .sameSite("Strict")
//            .build();
//
//    return ResponseEntity.ok()
//            .header(HttpHeaders.SET_COOKIE, cookie.toString())
//            .body(Map.of("accessToken", newAccessToken));
//}
//
