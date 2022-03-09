package com.soccer.pickupSoccer.utils.token;

import com.soccer.pickupSoccer.entities.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JwtTokenService {

   void saveJwtToken(JwtToken token);
   List<JwtToken> getTokens();
   void deleteTokenByUserId(User user);
   int updateToken( JwtToken token, int id);
}
