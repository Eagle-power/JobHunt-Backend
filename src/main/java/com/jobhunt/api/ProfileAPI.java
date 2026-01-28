package com.jobhunt.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jobhunt.dto.ProfileDTO; 
import com.jobhunt.exception.JobPortalException;
import com.jobhunt.service.ProfileService;
 

@RestController
@CrossOrigin
@Validated
@RequestMapping("/profiles")
public class ProfileAPI {
	@Autowired
	private ProfileService profileService;
	
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ProfileDTO>getProfile(@PathVariable Long id) throws  JobPortalException{ 
		return new ResponseEntity<>(profileService.getProfile(id), HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<ProfileDTO>>getAllProfiles() throws  JobPortalException{ 
		return new ResponseEntity<>(profileService.getAllProfiles(), HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ProfileDTO>updateProfile(@RequestBody ProfileDTO profileDTO) throws  JobPortalException{ 
		return new ResponseEntity<>(profileService.updateProfile(profileDTO), HttpStatus.OK);
	}
	
	@PostMapping(
		    value = "/uploadResume/{id}",
		    consumes = "multipart/form-data"
		)
		public ResponseEntity<String> uploadResume(
		        @PathVariable Long id,
		        @RequestParam("file") MultipartFile file
		) throws JobPortalException {

		    // 1️⃣ basic validations
		    if (file == null || file.isEmpty()) {
		        return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
		    }

		    if (!"application/pdf".equalsIgnoreCase(file.getContentType())) {
		        return new ResponseEntity<>("Only PDF files are allowed", HttpStatus.BAD_REQUEST);
		    }

		    // 2️⃣ size check (5 MB)
		    long MAX_SIZE = 5 * 1024 * 1024;
		    if (file.getSize() > MAX_SIZE) {
		        return new ResponseEntity<>("Resume size must be <= 5MB", HttpStatus.BAD_REQUEST);
		    }

		    // 3️⃣ delegate to service
		    profileService.uploadResume(id, file);

		    return new ResponseEntity<>("Resume uploaded successfully", HttpStatus.OK);
		}

	   
	
	@GetMapping("/downloadResume/{id}")
	public ResponseEntity<byte[]> downloadResume(
	        @PathVariable Long id
	) throws JobPortalException {

	    byte[] pdf = profileService.downloadResume(id);

	    return ResponseEntity.ok()
	        .header("Content-Disposition", "inline; filename=resume.pdf")
	        .header("Content-Type", "application/pdf")
	        .body(pdf);
	}

	
	@DeleteMapping("/deleteResume/{id}")
	public ResponseEntity<String> deleteResume(
	        @PathVariable Long id
	) throws JobPortalException {

	    profileService.deleteResume(id);
	    return new ResponseEntity<>("Resume deleted successfully", HttpStatus.OK);
	}


	
	
}
