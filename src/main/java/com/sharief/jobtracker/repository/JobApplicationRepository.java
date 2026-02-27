package com.sharief.jobtracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharief.jobtracker.entity.JobApplication;
import com.sharief.jobtracker.entity.User;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByUser(User user);
    
    Optional<JobApplication> findByIdAndUser(Long id, User user);
}