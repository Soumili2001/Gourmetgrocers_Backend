package com.soumili.userservice.Services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import com.soumili.userservice.entities.User;
import com.soumili.userservice.repository.UserRepository;
import com.soumili.userservice.services.UserService;

@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@MockBean
	private UserRepository userRepo;
	
	@MockBean
	private RestTemplate resttemplate;
	
	@Test
	public void registeruser() {
		User user = new User(11, "Soumili", "12345#ABC", "sarsoubiss@gmail.com", "Soumili Biswas", "user", false, null,null);
	
		when(userRepo.findUserByUserName("soumili") ).thenReturn(null);
		when(userRepo.findUserByEmail("sarsoubiss@gmail.com")).thenReturn(null);
		
		userService.registerUser(user);
		verify(userRepo,times(1)).save(user);
	}
	
		
		

}
