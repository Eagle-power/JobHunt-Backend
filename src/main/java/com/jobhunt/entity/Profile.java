package com.jobhunt.entity;

import java.util.Base64;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.jobhunt.dto.Certification;
import com.jobhunt.dto.Experience;
import com.jobhunt.dto.ProfileDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="profiles")
public class Profile {
	@Id
	private Long id;
	private String name;
	private String email;
	private String jobTitle;
	private String company;
	private String location;
	private String about;
	private byte[] picture;
	 // 🔥 RESUME
	 private byte[] resumePdf;
	 private String resumeFileName;
	 private String resumeText;
   
    
	private Long totalExp;
	private List<String>skills;
	private List<Experience>experiences;
	private List<Certification>certifications;
	private List<Long>savedJobs;
	
	public ProfileDTO toDtO() {
		return new ProfileDTO(
				this.id,
				this.name, 
				this.email, 
				this.jobTitle, 
				this.company, 
				this.location, 
				this.about, 
				this.picture!=null?Base64.getEncoder().encodeToString(this.picture):null , 
						 this.resumePdf != null
			                ? Base64.getEncoder().encodeToString(this.resumePdf)
			                : null,
			            this.resumeFileName,
			    this.resumeText,
				this.totalExp, 
				this.skills, 
				this.experiences, 
				this.certifications, 
				this.savedJobs);
	}
}
