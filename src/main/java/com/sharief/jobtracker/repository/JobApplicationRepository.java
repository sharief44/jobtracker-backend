package com.sharief.jobtracker.repository;

import com.sharief.jobtracker.entity.JobApplication;
import com.sharief.jobtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByUser(User user);
}