package com.jobhunt.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jobhunt.dto.UserDTO;
import com.jobhunt.exception.JobPortalException;
import com.jobhunt.service.UserService;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 try {
			UserDTO dto = userService.getUserByEmail(email);
			return new CustomUserDetails(dto.getId(), email , dto.getName(), dto.getPassword() , dto.getProfileId(), dto.getAccountType() , new ArrayList<>());
		 } catch (JobPortalException e) {
			 
			e.printStackTrace();
		 }
		 return null;
	}

}
