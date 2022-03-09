package com.soccer.pickupSoccer.contollers;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import com.soccer.pickupSoccer.business.concretes.UserManager;
import com.soccer.pickupSoccer.entities.AuthenticationRequest;
import com.soccer.pickupSoccer.utils.JwtUtil;
import com.soccer.pickupSoccer.utils.token.JwtToken;
import com.soccer.pickupSoccer.utils.token.JwtTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.soccer.pickupSoccer.business.abstracts.UserService;
import com.soccer.pickupSoccer.entities.User;

@RestController
@RequestMapping(value="api/game")
public class UsersController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserManager userManager;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenServiceImpl jwtTokenServiceImpl;

	@PostMapping(value = "/register")
	public ResponseEntity<?> addUser(@RequestBody User user) throws Exception {
		try {
		URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/game/register").toString());	
		return ResponseEntity.created(uri).body(userService.saveUser(user));
		}catch(Exception e){
			e.getMessage();
			return  ResponseEntity.badRequest().body(userService.saveUser(user));
		}
	}
	@PostMapping(value = "/auth/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) throws Exception{
		try{
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
														request.getPassword()));
		}catch (BadCredentialsException e){
			throw new Exception("Incorrect username and password",e);
		}
		final UserDetails userDetails=userManager.loadUserByUsername(request.getUsername());
		User user=(User) userDetails;
		final String jwt=jwtUtil.generateToken(userDetails);
//		List<JwtToken> userTokens= jwtTokenService.getTokens();
//		List<JwtToken> allTokens=jwtTokenService.getTokens();
//		for (JwtToken jwt_token:allTokens){
//			if(jwt_token.getUser().getUserId()==user.getUserId()){
//				jwtTokenService.deleteTokenByUserId(jwt_token.getId());
//			}
//		}
//		jwtTokenServiceImpl.deleteTokenByUserId(user);
		JwtToken jwtToken=new JwtToken(jwt, LocalDateTime.now(),jwtUtil.extractExpiration(jwt),user);
		jwtTokenServiceImpl.updateToken(jwtToken,user.getUserId());
		jwtTokenServiceImpl.saveJwtToken(jwtToken);

		if(jwtUtil.isTokenExpired(jwt)){
			jwtUtil.generateRefreshToken(jwt,(User)userDetails);
		}
		return  ResponseEntity.ok(jwtToken);
	}

	@DeleteMapping(value = "/register/id={id}")
	public ResponseEntity<String> deleteRegistration(@PathVariable("id") int id){
		userService.deleteById(id);
		return new ResponseEntity<>("Deleted User with ID: "+id,HttpStatus.OK);
	}
	@GetMapping(value = "/registrations")
	public ResponseEntity<List<User>> getAllRegistrations(){
		return ResponseEntity.ok().body(userService.getUsers());
	}
}

