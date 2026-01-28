package com.jobhunt.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jobhunt.dto.ProfileDTO;
import com.jobhunt.exception.JobPortalException;

public interface ProfileService {
	public Long createProfile(String email) throws JobPortalException;
	public ProfileDTO getProfile(Long id) throws JobPortalException;
	public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobPortalException;
	public List<ProfileDTO> getAllProfiles();
	void uploadResume(Long id, MultipartFile file) throws JobPortalException;
	byte[] downloadResume(Long id) throws JobPortalException;
	void deleteResume(Long id) throws JobPortalException;

}
