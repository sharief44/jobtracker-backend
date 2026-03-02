package com.sharief.jobtracker.controller;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.sharief.jobtracker.entity.JobStatus;
import com.sharief.jobtracker.service.JobApplicationService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final JobApplicationService jobService;

    public AdminController(JobApplicationService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/jobs/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<JobStatus, Long> getAllStats() {
        return jobService.getAllJobStats();
    }
}