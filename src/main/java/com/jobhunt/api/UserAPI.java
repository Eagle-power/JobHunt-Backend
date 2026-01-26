package com.jobhunt.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobhunt.dto.LoginDTO;
import com.jobhunt.dto.ResponseDTO;
import com.jobhunt.dto.UserDTO;
import com.jobhunt.exception.JobPortalException;
import com.jobhunt.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/users")


public class UserAPI {
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<UserDTO>registerUser(@RequestBody @Valid UserDTO UserDTO) throws JobPortalException{
		UserDTO = userService.registerUser(UserDTO);
		return new ResponseEntity<>(UserDTO , HttpStatus.CREATED);
	}
	
	
	
	@PostMapping("/login")
	public ResponseEntity<UserDTO>loginUser(@RequestBody @Valid LoginDTO loginDTO) throws JobPortalException{
		
		return new ResponseEntity<>(userService.loginUser(loginDTO), HttpStatus.OK);
	}
	
	@PostMapping("/changePass")
	public ResponseEntity<ResponseDTO>changePassword(@RequestBody @Valid LoginDTO loginDTO) throws JobPortalException{
		
		return new ResponseEntity<>(userService.changePassword(loginDTO), HttpStatus.OK);
	}
	
	@PostMapping("/sendOtp/{email}")
	public ResponseEntity<ResponseDTO>SendOtp(@PathVariable @Email(message = "{user.email.invalid}") String email) throws  Exception{
		userService.sendOtp(email);
		return new ResponseEntity<>(new ResponseDTO("Otp sent successfully."), HttpStatus.OK);
	}
	
	@GetMapping("/verifyOtp/{email}/{otp}")
	public ResponseEntity<ResponseDTO>verifyOtp(@PathVariable @Email(message = "{user.email.invalid}") String email , @PathVariable @Pattern(regexp = "^[0-9]{6}$", message = "{otp.invalid}" ) String otp) throws  JobPortalException{
		userService.verifyOtp(email , otp);
		return new ResponseEntity<>(new ResponseDTO("Otp has been verified successfully."), HttpStatus.OK);
	}
	
}
