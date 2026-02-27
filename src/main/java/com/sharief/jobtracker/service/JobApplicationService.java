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
	//update job
	public JobResponse updateJob(Long jobId, JobApplication updatedJob) {

	    String email = SecurityContextHolder.getContext()
	            .getAuthentication()
	            .getName();

	    User user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    JobApplication existingJob = jobRepository
	            .findByIdAndUser(jobId, user)
	            .orElseThrow(() -> new RuntimeException("Job not found or access denied"));

	    existingJob.setCompanyName(updatedJob.getCompanyName());
	    existingJob.setPosition(updatedJob.getPosition());
	    existingJob.setStatus(updatedJob.getStatus());
	    existingJob.setNotes(updatedJob.getNotes());
	    existingJob.setAppliedDate(updatedJob.getAppliedDate());
	    existingJob.setInterviewDate(updatedJob.getInterviewDate());

	    JobApplication savedJob = jobRepository.save(existingJob);

	    return new JobResponse(
	            savedJob.getId(),
	            savedJob.getCompanyName(),
	            savedJob.getPosition(),
	            savedJob.getStatus(),
	            savedJob.getAppliedDate(),
	            savedJob.getInterviewDate(),
	            savedJob.getNotes(),
	            savedJob.getCreatedAt()
	    );
	}
	
	//delete job
	public void deleteJob(Long jobId) {

	    String email = SecurityContextHolder.getContext()
	            .getAuthentication()
	            .getName();

	    User user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    JobApplication job = jobRepository
	            .findByIdAndUser(jobId, user)
	            .orElseThrow(() -> new RuntimeException("Job not found or access denied"));

	    jobRepository.delete(job);
	}
}
