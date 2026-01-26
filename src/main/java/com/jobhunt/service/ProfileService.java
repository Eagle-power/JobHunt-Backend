package com.jobhunt.service;

import java.util.List;


import com.jobhunt.dto.ProfileDTO;
import com.jobhunt.exception.JobPortalException;

public interface ProfileService {
	public Long createProfile(String email) throws JobPortalException;
	public ProfileDTO getProfile(Long id) throws JobPortalException;
	public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobPortalException;
	public List<ProfileDTO> getAllProfiles();
}
