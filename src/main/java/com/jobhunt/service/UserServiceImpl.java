package com.jobhunt.service;

import java.time.Instant;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobhunt.dto.LoginDTO;
import com.jobhunt.dto.NotificationDTO;
import com.jobhunt.dto.ResponseDTO;
import com.jobhunt.dto.UserDTO;
import com.jobhunt.entity.OTP;
import com.jobhunt.entity.User;
import com.jobhunt.exception.JobPortalException;
import com.jobhunt.repository.OTPRepository;
import com.jobhunt.repository.UserRepository;
import com.jobhunt.utility.Data;
import com.jobhunt.utility.Utilities;
 
import jakarta.mail.internet.MimeMessage;

@Service(value="userService")

public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OTPRepository otpRepository;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private NotificationService notificationService;

	@Override
	public UserDTO registerUser(UserDTO userDTO)throws JobPortalException {
		Optional<User> optional = userRepository.findByEmail(userDTO.getEmail());
		if(optional.isPresent()) throw new JobPortalException("USER_FOUND");
		userDTO.setProfileId(profileService.createProfile(userDTO.getEmail()));
		
		userDTO.setId(Utilities.getNextSequence("users"));
		userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		  
		User user = userDTO.toEntity();
		userRepository.save(user);
		
		return user.toDTO();
	}

	@Override
	public UserDTO loginUser(LoginDTO loginDTO)throws JobPortalException {
		 User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(()-> new JobPortalException("USER_NOT_FOUND"));
		 if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) throw new JobPortalException("INVALID_CREDENTIALS");
				 
		 return user.toDTO();
	}

	@Override
	public Boolean sendOtp(String email) throws Exception {
		 User user = userRepository.findByEmail(email).orElseThrow(()-> new JobPortalException("USER_NOT_FOUND"));
		
		MimeMessage mm = mailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mm, true); 
		message.setTo(email);
		message.setSubject("You JobHunt OTP code");
		String genOtp = Utilities.generateOtp();
		OTP otp = new OTP(email , genOtp , Instant.now());
		otpRepository.save(otp);
		message.setText(Data.getMessageBody(genOtp , user.getName()),true);
		mailSender.send(mm);
		
		return true;
	}

	@Override
	public Boolean verifyOtp(String email , String otp) throws JobPortalException {
		 OTP otpEntity = otpRepository.findById(email).orElseThrow(()->new JobPortalException("OTP_NOT_FOUND"));
		 if(!otpEntity.getOtpCode().equals(otp)) throw new JobPortalException("OTP_INCORRECT");
		 
		 return true;
	}

	@Override
	public ResponseDTO changePassword(LoginDTO loginDTO) throws JobPortalException {
		User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(()-> new JobPortalException("USER_NOT_FOUND"));
		user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
		userRepository.save(user);
		NotificationDTO notify = new NotificationDTO();
		notify.setUserId(user.getId());
		notify.setMessage("password Reset Successfull.");
		notify.setAction("Password Reset");
		notificationService.sendNotification(notify);
		
		return new ResponseDTO("Password changed Successfully.");
		
	}
	 
	@Scheduled(fixedRate = 60000)
	public void removeExpireOTPs() {
		Instant expiry = Instant.now().minus(5, ChronoUnit.MINUTES);  
		
		List<OTP>expiredOTPs = otpRepository.findByCreationTimeBefore(expiry);
		if(!expiredOTPs.isEmpty()) {
			otpRepository.deleteAll(expiredOTPs);
			System.out.println("Removed "+expiredOTPs.size()+" Expired OTPs.");
		}
		
	}

	@Override
	public UserDTO getUserByEmail(String email) throws JobPortalException {
		return userRepository.findByEmail(email).orElseThrow(()-> new JobPortalException("USER_NOT_FOUND")).toDTO();
		
	}
	
	

}
