package com.sharief.jobtracker.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sharief.jobtracker.entity.JobApplication;
import com.sharief.jobtracker.entity.JobStatus;
import com.sharief.jobtracker.service.JobApplicationService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final JobApplicationService jobService;

    public AdminController(JobApplicationService jobService) {
        this.jobService = jobService;
    }

    // Admin analytics
    @GetMapping("/jobs/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<JobStatus, Long> getAllStats() {
        return jobService.getAllJobStats();
    }

    // Admin fetch all jobs
    @GetMapping("/jobs")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<JobApplication> getAllJobs(Pageable pageable) {
        return jobService.getAllJobs(pageable);
    }

    // ✅ Admin delete any job
    @DeleteMapping("/jobs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteJob(@PathVariable Long id) {

        jobService.adminDeleteJob(id);

        return "Job deleted successfully";
    }

    // ✅ Admin update job status
    @PutMapping("/jobs/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public JobApplication updateStatus(
            @PathVariable Long id,
            @RequestParam JobStatus status) {

        return jobService.adminUpdateStatus(id, status);
    }
}