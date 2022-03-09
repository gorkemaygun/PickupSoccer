package com.soccer.pickupSoccer.business.abstracts;

import java.util.List;
import java.util.Optional;

import com.soccer.pickupSoccer.entities.User;
import com.soccer.pickupSoccer.utils.token.JwtToken;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
	User saveUser(User user) throws Exception;
	User getUser(String username);
	List<User>getUsers();
	void deleteById(int id);
	//void getTokensByUserId(User user);
//	void removeDuplicateUserWithTokenRecords();
}
