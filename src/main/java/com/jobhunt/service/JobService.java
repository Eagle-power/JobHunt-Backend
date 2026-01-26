package com.jobhunt.service;

import java.util.List;

import com.jobhunt.dto.ApplicantDTO;
import com.jobhunt.dto.Application;
import com.jobhunt.dto.JobDTO;
import com.jobhunt.exception.JobPortalException;

import jakarta.validation.Valid;

public interface JobService {

	public JobDTO postJob(@Valid JobDTO jobDTO) throws JobPortalException;

	public List<JobDTO> getAllJobs();

	public JobDTO getJob(Long id)throws JobPortalException;

	public void applyJob(Long id, ApplicantDTO applicantDTO)throws JobPortalException;

	public List<JobDTO> getJobsPostedBy(Long id);

	public void changeAppStatus(Application application)throws JobPortalException;

	 
	
}
