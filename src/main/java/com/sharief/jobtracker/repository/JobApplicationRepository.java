package com.sharief.jobtracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    
    @Query("""
    	       SELECT j.status, COUNT(j)
    	       FROM JobApplication j
    	       WHERE j.user.id = :userId
    	       GROUP BY j.status
    	       """)
    	List<Object[]> countJobsByStatus(@Param("userId") Long userId);
}