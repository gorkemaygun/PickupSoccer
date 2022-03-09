package com.soccer.pickupSoccer.utils.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken,Integer> {

    Optional<JwtToken> findByToken(String token);
   // List<JwtToken> findTokensByUserId(int id);
    @Transactional
    @Modifying
    @Query("Update new com.soccer.pickupSoccer.utils.token.JwtToken j Set j.token:=token Where user.user_id:= id")
    int updateToken(@Param("token") JwtToken token, @Param("id") int id);
}
