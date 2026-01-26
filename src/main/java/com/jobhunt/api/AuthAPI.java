package com.jobhunt.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobhunt.jwt.AuthenticationRequest;
import com.jobhunt.jwt.AuthenticationResponse;
import com.jobhunt.jwt.jwtHelper;

@RestController
@CrossOrigin
@RequestMapping("/auth")

public class AuthAPI {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager  authenticationManager;
	@Autowired
	private jwtHelper jwtHelper;
	
	
	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticatinToken(@RequestBody AuthenticationRequest request){
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		final String jwt = jwtHelper.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	
	}
	
	
}
