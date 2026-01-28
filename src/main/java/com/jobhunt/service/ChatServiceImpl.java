package com.jobhunt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobhunt.dto.ChatRequestDTO;
import com.jobhunt.dto.ChatResponseDTO;
import com.jobhunt.entity.Job;
import com.jobhunt.entity.Profile;
import com.jobhunt.exception.JobPortalException;
import com.jobhunt.repository.JobRepository;
import com.jobhunt.repository.ProfileRepository;
import com.jobhunt.utility.OpenRouterClient;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private OpenRouterClient openRouterClient;
    
    @Autowired
    private JobRepository jobRepository;


    @Override
    public ChatResponseDTO ask(ChatRequestDTO request, String email)
            throws JobPortalException {

        Profile profile = profileRepository.findById(request.getProfileId())
                .orElseThrow(() -> new JobPortalException("PROFILE_NOT_FOUND"));

        // 🔐 Ownership check
        if (!profile.getEmail().equalsIgnoreCase(email)) {
            throw new JobPortalException("UNAUTHORIZED_PROFILE_ACCESS");
        }

        if (profile.getResumeText() == null || profile.getResumeText().isBlank()) {
            throw new JobPortalException("RESUME_NOT_UPLOADED");
        }

        String prompt;

        // 🔥 JOB-AWARE MODE
        if (request.getJobId() != null) {
            Job job = jobRepository.findById(request.getJobId())
                    .orElseThrow(() -> new JobPortalException("JOB_NOT_FOUND")); 

            prompt = buildJobAwarePrompt(
                    profile.getResumeText(),
                    job,
                    request.getMessage()
            );
        }
        // 🔹 RESUME-ONLY MODE
        else {
            prompt = buildResumeOnlyPrompt(
                    profile.getResumeText(),
                    request.getMessage()
            );
            
        }

        String aiReply = openRouterClient.ask(prompt);
        return new ChatResponseDTO(aiReply);
    }
    
    
    private String buildJobAwarePrompt(
            String resumeText,
            Job job,
            String userMessage
    ) {

        return """
    You are an AI Career Assistant for JobHunt.

    IMPORTANT CONTEXT:
    The job role is "%s". You MUST focus your evaluation strictly on this role.

    Your task:
    - Analyze how well the candidate’s resume aligns with the given job role.
    - Base your analysis primarily on:
      • Job title
      • Required skills
      • Job description (if provided)
    - Do NOT default to the resume alone.
    - Do NOT evaluate the user for a different role.

    STRICT RULES:
    1. Treat the job title as the primary signal for role expectations.
    2. Use job-required skills as the benchmark.
    3. If the resume aligns poorly with the role, clearly state the mismatch.
    4. Frame gaps as learning opportunities.
    5. Never hallucinate experience.

    RESPONSE STRUCTURE (MANDATORY):
    - ## Role Alignment Summary
    - ## Matching Skills
    - ## Missing or Weak Areas
    - ## Suggested Learning Path

    Use clean Markdown. Be concise and role-focused.

    CANDIDATE RESUME:
    --------------------
    %s
    --------------------

    JOB DETAILS:
    Role: %s
    Company: %s
    Required Skills:
    %s

    Job Description:
    %s

    USER QUESTION:
    %s
    """.formatted(
            job.getJobTitle(),
            resumeText.length() > 6000 ? resumeText.substring(0, 6000) : resumeText,
            job.getJobTitle(),
            job.getCompany(),
            String.join(", ", job.getSkillsRequired()), // IMPORTANT
            job.getDescription(),
            userMessage
        );
    }


    
    
    private String buildResumeOnlyPrompt(String resumeText, String userMessage) {
        return """
    You are an AI Career Assistant for JobHunt.

    Use ONLY the resume to answer the question.
    If something is missing, explain it politely and suggest growth.

    Use Markdown, headings, and bullet points.

    RESUME:
    --------------------
    %s
    --------------------

    USER QUESTION:
    %s
    """.formatted(
            resumeText.length() > 6000 ? resumeText.substring(0, 6000) : resumeText,
            userMessage
        );
    }

    

    private String buildPrompt(String resumeText, String userMessage) {

        return """
    You are an AI Career Assistant for a job portal called JobHunt.

    Your mission:
    - Help users understand their resume, skills, and career profile.
    - Be accurate, supportive, and growth-oriented.
    - Never invent information that is not present in the resume.

    IMPORTANT RULES:
    1. Use ONLY the information explicitly present in the resume.
    2. If a skill, tool, or experience is NOT found in the resume:
       - Do NOT say "not mentioned in your resume".
       - Instead, politely explain that it does not currently appear in the resume
         and frame it as a potential learning or improvement opportunity.
    3. Never guess or hallucinate experience.
    4. Keep responses concise, structured, and easy to read.
    5. Do NOT repeat the entire resume unless explicitly asked.

    RESPONSE STYLE (MANDATORY):
    - Use clean Markdown formatting
    - Use headings, bullet points, and short paragraphs
    - Be friendly, professional, and encouraging
    - Write as if advising the user directly

    RESUME CONTENT:
    --------------------
    %s
    --------------------

    USER QUESTION:
    %s

    Respond now following all the rules above.
    """.formatted(
            resumeText.length() > 6000
                    ? resumeText.substring(0, 6000)
                    : resumeText,
            userMessage
        );
    }
}
