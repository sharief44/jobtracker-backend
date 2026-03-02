package com.sharief.jobtracker.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sharief.jobtracker.dto.JobResponse;
import com.sharief.jobtracker.entity.JobApplication;
import com.sharief.jobtracker.entity.JobStatus;
import com.sharief.jobtracker.entity.User;
import com.sharief.jobtracker.exception.JobNotFoundException;
import com.sharief.jobtracker.repository.JobApplicationRepository;
import com.sharief.jobtracker.repository.UserRepository;

@Service
public class JobApplicationService {

    private final JobApplicationRepository jobRepository;
    private final UserRepository userRepository;

    public JobApplicationService(JobApplicationRepository jobRepository,
                                 UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    // ✅ Create Job
    public JobResponse addJob(JobApplication job, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        job.setUser(user);

        JobApplication savedJob = jobRepository.save(job);

        return mapToResponse(savedJob);
    }

    // ✅ Pagination + Filtering
    public Page<JobResponse> getUserJobs(Long userId,
                                         JobStatus status,
                                         Pageable pageable) {

        Page<JobApplication> jobs;

        if (status != null) {
            jobs = jobRepository.findByUserIdAndStatus(userId, status, pageable);
        } else {
            jobs = jobRepository.findByUserId(userId, pageable);
        }

        return jobs.map(this::mapToResponse);
    }

    // ✅ Update Job (Ownership Safe)
    public JobResponse updateJob(Long jobId,
                                 JobApplication updatedJob,
                                 Long userId) {

        JobApplication existingJob = jobRepository
                .findByIdAndUserId(jobId, userId)
                .orElseThrow(() -> new JobNotFoundException("Job not found or access denied"));

        existingJob.setCompanyName(updatedJob.getCompanyName());
        existingJob.setPosition(updatedJob.getPosition());
        existingJob.setStatus(updatedJob.getStatus());
        existingJob.setNotes(updatedJob.getNotes());
        existingJob.setAppliedDate(updatedJob.getAppliedDate());
        existingJob.setInterviewDate(updatedJob.getInterviewDate());

        JobApplication savedJob = jobRepository.save(existingJob);

        return mapToResponse(savedJob);
    }

    // ✅ Delete Job (Ownership Safe)
    public void deleteJob(Long jobId, Long userId) {

        JobApplication job = jobRepository
                .findByIdAndUserId(jobId, userId)
                .orElseThrow(() -> new JobNotFoundException("Job not found or access denied"));

        jobRepository.delete(job);
    }

    // 🔥 Entity → DTO mapper
    private JobResponse mapToResponse(JobApplication job) {
        return new JobResponse(
                job.getId(),
                job.getCompanyName(),
                job.getPosition(),
                job.getStatus(),
                job.getAppliedDate(),
                job.getInterviewDate(),
                job.getNotes(),
                job.getCreatedAt()
        );
    }
    
    public Map<JobStatus, Long> getJobStats(Long userId) {

        List<Object[]> results = jobRepository.countJobsByStatus(userId);

        Map<JobStatus, Long> stats = new EnumMap<>(JobStatus.class);

        // Initialize all statuses with 0
        for (JobStatus status : JobStatus.values()) {
            stats.put(status, 0L);
        }

        // Fill actual counts
        for (Object[] row : results) {
            JobStatus status = (JobStatus) row[0];
            Long count = (Long) row[1];
            stats.put(status, count);
        }

        return stats;
    }
}