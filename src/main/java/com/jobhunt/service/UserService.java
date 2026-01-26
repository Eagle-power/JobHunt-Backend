package com.jobhunt.service;

import com.jobhunt.dto.LoginDTO;

import com.jobhunt.dto.ResponseDTO;
import com.jobhunt.dto.UserDTO;
import com.jobhunt.exception.JobPortalException;

 

public interface UserService {

	public UserDTO registerUser(UserDTO userDTO) throws JobPortalException;
	
	public UserDTO getUserByEmail(String email)throws JobPortalException;

	public UserDTO loginUser(LoginDTO loginDTO) throws JobPortalException;

	public Boolean sendOtp(String email) throws  Exception ;

	public Boolean verifyOtp(String email , String otp)throws JobPortalException;

	public ResponseDTO changePassword(LoginDTO loginDTO)throws JobPortalException;
}
