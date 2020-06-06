package com.aea.diet.dao;

import java.util.List;

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

	int deleteByEmail(String user);
	
	@Modifying
	@Query("update User u set u.mobile = ?2 where u.email = ?1")
	int updateMobileByEmail(String Email, String newMobile);
	
	@Modifying
	@Query("update User u set u.role = ?2, u.groupName = ?3, u.batchName = ?4 where u.email = ?1")
	int updateDetailsByEmail(String Email, String role, String groupName, String batchName);

	void deleteByRole(String string);

	List<User> findByBatchName(String batch);

	List<User> findByRole(String string);

}
