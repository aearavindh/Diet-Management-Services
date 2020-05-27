package com.aea.diet.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.aea.diet.model.Challenger;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ChallengerRepositoryTest {
    
	@Autowired
	private ChallengerRepository repository;
	
	Challenger mockChallenger = new Challenger("a","b","c","d","e","f","g","h","i","j","k",
			"l","m","n","o","p","q","r","s","t","u");
	
	@Test
	public void saveTest() {
		Challenger challenger = repository.save(mockChallenger);
		assertEquals(mockChallenger, challenger);
	}

}
