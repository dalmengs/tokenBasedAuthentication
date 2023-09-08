package com.baseproject.tokenbasedauthentication.Repository;

import com.baseproject.tokenbasedauthentication.Entity.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Transactional
    @Modifying
    @Query("delete from RefreshToken r where r.expireTime <= :now")
    void deleteAllExpiredToken(@Param("now") LocalDateTime now);

    Optional<RefreshToken> findByAccessToken(String accessToken);
}
