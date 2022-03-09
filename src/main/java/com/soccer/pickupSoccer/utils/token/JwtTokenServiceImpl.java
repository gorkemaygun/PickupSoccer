package com.soccer.pickupSoccer.utils.token;

import com.soccer.pickupSoccer.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    @Autowired
    private final JwtTokenRepository jwtTokenRepository;

    @Override
    public void saveJwtToken(JwtToken token){
        jwtTokenRepository.save(token);
    }
    @Override
    public List<JwtToken> getTokens(){
        return jwtTokenRepository.findAll();
    }
    @Override
    public void deleteTokenByUserId(User user){
        List<JwtToken> allTokens=getTokens();
        for (JwtToken jwt_token:allTokens){
            if(jwt_token.getUser().getUserId()==user.getUserId()){
                jwtTokenRepository.deleteById(jwt_token.getId());
           }
        }

    }

    @Override
    public int updateToken(JwtToken token, int id) {
        return jwtTokenRepository.updateToken(token,id);
    }
//    public  List<JwtToken> loadTokensByUserId(int id){
//        return jwtTokenRepository.findTokensByUserId(id);
//    }
}
