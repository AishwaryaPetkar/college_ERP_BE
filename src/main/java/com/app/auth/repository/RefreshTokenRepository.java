package com.app.auth.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.auth.entities.RefreshToken;
import com.app.auth.entities.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Query("SELECT r FROM RefreshToken r JOIN FETCH r.user WHERE r.token = :token")
    Optional<RefreshToken> findByTokenWithUser(@Param("token") String token);
	void deleteByUser(User user);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM RefreshToken r WHERE r.user = :user")
	void deleteAllByUser(@Param("user") User user);
}