package com.soccer.pickupSoccer.business.concretes;

import java.util.List;
import javax.transaction.Transactional;
import com.soccer.pickupSoccer.utils.EmailValidator;
import com.soccer.pickupSoccer.utils.token.JwtTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.soccer.pickupSoccer.business.abstracts.UserService;
import com.soccer.pickupSoccer.dataAccess.abstracts.UserDao;
import com.soccer.pickupSoccer.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserManager implements UserService,UserDetailsService{
	@Autowired
	private final UserDao userDao;
	private final EmailValidator emailValidator;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JwtTokenServiceImpl jwtTokenServiceImpl;
//	@Autowired
//	private final JwtUtil jwtUtil;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails= userDao.findByUsername(username);
		if(userDetails== null) {
			throw new UsernameNotFoundException(String.format("User with %s not found ",username));
		}
		return  userDetails;
	}

	@Override
	public User saveUser(User newUser) throws IllegalStateException {
		// indexleme ye bak


		User user=userDao.findByUsername(newUser.getUsername());
		if(user !=null){
			throw new IllegalStateException("The username has already taken");
		}

		boolean isValidEmail= emailValidator.test(newUser.getEmail());
		if (!isValidEmail){
			throw new IllegalStateException("Email is not valid");
		}

		log.info("Saving a new user {} to the database",newUser.getUsername());
		String encodedPassword=bCryptPasswordEncoder.encode(newUser.getPassword());
		newUser.setPassword(encodedPassword);

		User savedUser=userDao.save(newUser);
		return savedUser;
	}

	@Override
	public User getUser(String username) {
		log.info("Fetching user {} ",username);
		return userDao.findByUsername(username);
	}

	@Override
	public List<User> getUsers() {
		log.info("Fetching all users");
		return (List<User>) userDao.findAll();
	}

	@Override
	public void deleteById(int id) { 
		userDao.deleteById(id);	
	}
	


//	@Override
//	public void removeDuplicateUserWithTokenRecords() {
//		userDao.removeDuplicateUserWithTokenRecords();
//	}

//	@Override
//	public void getTokensByUserId(User user) {
//
//	}




//	}

//	public int enableAppUser(String username){
//		return userDao.enableAppUser(username);
//	}
//

}
