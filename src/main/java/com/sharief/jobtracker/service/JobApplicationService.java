package com.sharief.jobtracker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sharief.jobtracker.dto.JobResponse;
import com.sharief.jobtracker.entity.JobApplication;
import com.sharief.jobtracker.entity.User;
import com.sharief.jobtracker.repository.JobApplicationRepository;
import com.sharief.jobtracker.repository.UserRepository;

@Service
public class JobApplicationService {
	
	private final JobApplicationRepository jobRepository;
	private final UserRepository userRepository;
	
	public JobApplicationService(JobApplicationRepository jobRepository,UserRepository userRepository) {
		this.jobRepository=jobRepository;
		this.userRepository=userRepository;
	}

	public JobApplication addJob(JobApplication job) {
		
		String email = SecurityContextHolder.getContext()
		              .getAuthentication()
		              .getName();
		
		User user = userRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException("User not found"));
		
		job.setUser(user);
		
		return jobRepository.save(job);
	}
	
	public List<JobResponse> getMyJobs() {

	    String email = SecurityContextHolder.getContext()
	            .getAuthentication()
	            .getName();

	    User user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    return jobRepository.findByUser(user)
	            .stream()
	            .map(job -> new JobResponse(
	                    job.getId(),
	                    job.getCompanyName(),
	                    job.getPosition(),
	                    job.getStatus(),
	                    job.getAppliedDate(),
	                    job.getInterviewDate(),
	                    job.getNotes(),
	                    job.getCreatedAt()
	            ))
	            .collect(Collectors.toList());
	}
}
