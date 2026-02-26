package com.sharief.jobtracker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sharief.jobtracker.entity.JobApplication;
import com.sharief.jobtracker.service.JobApplicationService;

@RestController
@RequestMapping("/api/jobs")
public class JobApplicationController {

	private final JobApplicationService jobService;
	
	public JobApplicationController(JobApplicationService jobService) {
		this.jobService = jobService;
	}
	
	@PostMapping
	public JobApplication addJob(@RequestBody JobApplication job) {
		return jobService.addJob(job);
	}
	
	@GetMapping
	public List<JobApplication> getMyjob(){
		return jobService.getMyJob();	}
}
