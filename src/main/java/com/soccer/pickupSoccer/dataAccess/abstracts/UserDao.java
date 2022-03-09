package com.soccer.pickupSoccer.dataAccess.abstracts;

import com.soccer.pickupSoccer.utils.token.JwtToken;
import org.hibernate.cache.spi.entry.StructuredCacheEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import com.soccer.pickupSoccer.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserDao extends JpaRepository<User, Integer>{

	 User findByUsername(String username);
//	 @Query("delete from JwtToken j, User u WHERE j.user_id=:u.user_id")
//	 void removeDuplicateUserWithTokenRecords();
//	 @Transactional
//	 @Modifying
//	 @Query("UPDATE User u SET u.enabled = TRUE WHERE u.username= ?1")
//	 int enableAppUser(String username);
}
