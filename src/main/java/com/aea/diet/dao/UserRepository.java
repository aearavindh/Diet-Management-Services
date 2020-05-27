package com.aea.diet.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.aea.diet.model.User;

@Transactional
public interface UserRepository extends JpaRepository<User, String> {
	
	User findByEmail(String email);
	
	@Modifying
	@Query("update User u set u.password = ?2 where u.email = ?1")
	int updatePasswordByEmail(String Email, String newPassword);

}
