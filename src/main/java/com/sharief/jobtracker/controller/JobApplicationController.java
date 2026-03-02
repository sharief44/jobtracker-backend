package com.sharief.jobtracker.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sharief.jobtracker.dto.JobResponse;
import com.sharief.jobtracker.entity.JobApplication;
import com.sharief.jobtracker.entity.JobStatus;
import com.sharief.jobtracker.service.JobApplicationService;
import com.sharief.jobtracker.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/jobs")
public class JobApplicationController {

    private final JobApplicationService jobService;
    private final UserService userService;

    public JobApplicationController(JobApplicationService jobService,
                                    UserService userService) {
        this.jobService = jobService;
        this.userService = userService;
    }

    //Create Job
    @PostMapping
    public JobResponse addJob(@Valid @RequestBody JobApplication job,
                              Principal principal) {

        Long userId = userService
                .getUserByEmail(principal.getName())
                .getId();

        return jobService.addJob(job, userId);
    }

 // Get Jobs with Pagination + Filtering + Sorting
    @GetMapping
    public Page<JobResponse> getUserJobs(
            @RequestParam(required = false) JobStatus status,
            Pageable pageable,
            Principal principal) {

        Long userId = userService
                .getUserByEmail(principal.getName())
                .getId();

        return jobService.getUserJobs(userId, status, pageable);
    }

    // Update Job
    @PutMapping("/{id}")
    public JobResponse updateJob(@PathVariable Long id,
                                 @RequestBody JobApplication job,
                                 Principal principal) {

        Long userId = userService
                .getUserByEmail(principal.getName())
                .getId();

        return jobService.updateJob(id, job, userId);
    }

    // ✅ Delete Job
    @DeleteMapping("/{id}")
    public String deleteJob(@PathVariable Long id,
                            Principal principal) {

        Long userId = userService
                .getUserByEmail(principal.getName())
                .getId();

        jobService.deleteJob(id, userId);

        return "Job deleted successfully";
    }
    
    @GetMapping("/stats")
    public Map<JobStatus, Long> getStats(Principal principal) {

        Long userId = userService
                .getUserByEmail(principal.getName())
                .getId();

        return jobService.getJobStats(userId);
    }
}