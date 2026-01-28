package com.jobhunt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jobhunt.dto.ProfileDTO;
import com.jobhunt.entity.Profile;
import com.jobhunt.exception.JobPortalException;
import com.jobhunt.repository.ProfileRepository;
import com.jobhunt.utility.PdfTextExtractor;
import com.jobhunt.utility.Utilities;

@Service("profilesService")
public class ProfileServiceImpl implements ProfileService{
	
	@Autowired
	private ProfileRepository profileRepository	;

	@Override
	public Long createProfile(String email) throws JobPortalException {
		Profile profile = new Profile();
		profile.setId(Utilities.getNextSequence("profiles"));
		profile.setEmail(email);
		profile.setSkills(new ArrayList<>());
		profile.setExperiences(new ArrayList<>());
		profile.setCertifications(new ArrayList<>());
		
		profileRepository.save(profile);
		return profile.getId();
	}

	@Override
	public ProfileDTO getProfile(Long id) throws JobPortalException {
		 return profileRepository.findById(id).orElseThrow(()-> new JobPortalException("PROFILE_NOT_FOUND")).toDtO();
	}

	@Override
	public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobPortalException {
		profileRepository.findById(profileDTO.getId()).orElseThrow(()-> new JobPortalException("PROFILE_NOT_FOUND"));
		profileRepository.save(profileDTO.toEntity());
		return profileDTO;
	}

	@Override
	public List<ProfileDTO> getAllProfiles() {
		return profileRepository.findAll().stream().map((x)->x.toDtO()).toList();
	}

	@Override
	public void uploadResume(Long id, MultipartFile file) throws JobPortalException {
		 if (file.getSize() > 5 * 1024 * 1024) {
		        throw new JobPortalException("RESUME_SIZE_EXCEEDED");
		    }

		    Profile profile = profileRepository.findById(id)
		        .orElseThrow(() -> new JobPortalException("PROFILE_NOT_FOUND"));

		    try {
		        profile.setResumePdf(file.getBytes());
		        profile.setResumeFileName(file.getOriginalFilename());
		        String resumeText = PdfTextExtractor.extract(file.getBytes());
		        profile.setResumeText(resumeText);

		        profileRepository.save(profile);

		    } catch (Exception e) {
		        throw new JobPortalException("RESUME_UPLOAD_FAILED");
		    }
	}
	
	@Override
	public byte[] downloadResume(Long id) throws JobPortalException {

	    Profile profile = profileRepository.findById(id)
	        .orElseThrow(() -> new JobPortalException("PROFILE_NOT_FOUND"));

	    if (profile.getResumePdf() == null) {
	        throw new JobPortalException("RESUME_NOT_FOUND");
	    }

	    return profile.getResumePdf();
	}

	@Override
	public void deleteResume(Long id) throws JobPortalException {

	    Profile profile = profileRepository.findById(id)
	        .orElseThrow(() -> new JobPortalException("PROFILE_NOT_FOUND"));

	    profile.setResumePdf(null);
	    profile.setResumeFileName(null);

	    profileRepository.save(profile);
	}

	
}
