package com.soccer.pickupSoccer.entities;

import java.util.Collection;
import java.util.Collections;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User implements UserDetails  {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	private int userId;
	@Column(name="company_name")
	private String companyName;
	@Column(name="company_address")
	private String address;
	@Column(name="email")
	private String email;
	@Column(name="username")
	private String username;
	@Column(name="password")
	private String password;
	@Column(name="phone_number")
	private String phoneNumber;
	@Column(name="image_url")
	private String imageUrl;
	@Column(name="role_name")
	@Enumerated(EnumType.STRING)
	private Role role;
	private boolean locked=false;
	private boolean enabled=false;

	public User(String companyName, String address, String email,
				String username, String password, String phoneNumber,
				String imageUrl, Role role) {
		this.companyName = companyName;
		this.address = address;
		this.email = email;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.imageUrl = imageUrl;
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority=new SimpleGrantedAuthority(role.name());
		return Collections.singleton(authority);
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
