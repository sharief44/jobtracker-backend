package com.sharief.jobtracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sharief.jobtracker.entity.JobApplication;
import com.sharief.jobtracker.entity.JobStatus;
import com.sharief.jobtracker.entity.User;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    // Old method (optional)
    List<JobApplication> findByUser(User user);

    // 🔥 Updated ownership-safe method
    Optional<JobApplication> findByIdAndUserId(Long id, Long userId);

    // Pagination
    Page<JobApplication> findByUserId(Long userId, Pageable pageable);

    // Pagination + Filtering
    Page<JobApplication> findByUserIdAndStatus(Long userId, JobStatus status, Pageable pageable);
}