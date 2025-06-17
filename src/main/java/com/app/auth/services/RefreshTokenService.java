package com.app.auth.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.auth.entities.RefreshToken;
import com.app.auth.entities.User;
import com.app.auth.repository.RefreshTokenRepository;
import com.app.auth.repository.UserRepository;



@Service
public class RefreshTokenService {

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

//    @Autowired
//    private UserRepository userRepository;

    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
    
    public Optional<RefreshToken> findByTokenWithUser(String token) {
        return refreshTokenRepository.findByTokenWithUser(token);
    }	


    public boolean isExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(Instant.now());
    }
    
    @Transactional
    public void delete(RefreshToken token) {
        refreshTokenRepository.delete(token);
    }
    
    @Transactional
    public void deleteAllUserTokens(User user) {
        refreshTokenRepository.deleteAllByUser(user);
    }

}
