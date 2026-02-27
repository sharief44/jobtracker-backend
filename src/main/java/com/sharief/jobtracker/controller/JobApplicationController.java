package com.sharief.jobtracker.controller;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.sharief.jobtracker.dto.JobResponse;
import com.sharief.jobtracker.entity.JobApplication;
import com.sharief.jobtracker.entity.JobStatus;
import com.sharief.jobtracker.service.JobApplicationService;
import com.sharief.jobtracker.service.UserService;

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

    // ✅ Create Job
    @PostMapping
    public JobResponse addJob(@RequestBody JobApplication job,
                              Principal principal) {

        Long userId = userService
                .getUserByEmail(principal.getName())
                .getId();

        return jobService.addJob(job, userId);
    }

    // ✅ Get Jobs with Pagination + Filtering
    @GetMapping
    public Page<JobResponse> getUserJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) JobStatus status,
            Principal principal) {

        Pageable pageable = PageRequest.of(page, size);

        Long userId = userService
                .getUserByEmail(principal.getName())
                .getId();

        return jobService.getUserJobs(userId, status, pageable);
    }

    // ✅ Update Job
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
}