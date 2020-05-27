package com.aea.diet.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.aea.diet.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepository;
	
	User mockUser = new User("a","b","c","$2a$10$JVgaATghSKBNlFqjn1VENu59mX3KZVp2ftpskR1Xm9LwwqZqA2kTq","e","f","g","h");
	
	@Before
	public void saveMockData() {
		userRepository.save(mockUser);
	}
	
	@Test
	public void findByEmailTest() {
		User user = userRepository.findByEmail("a");
		assertEquals("a", user.getEmail());
	}
	
	@Test
	public void updatePasswordByEmailTest() {
		int rowsAffected = userRepository.updatePasswordByEmail("a", "$2a$10$JVgaATghSKBNlFqjn1VENu59mX3KZVp2ftpskR1Xm9LwwqZqA2kTq");
		assertEquals(1, rowsAffected);
	}


}
