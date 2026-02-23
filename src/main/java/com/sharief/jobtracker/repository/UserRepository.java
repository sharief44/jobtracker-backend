package com.sharief.jobtracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharief.jobtracker.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

	Optional<User> findByEmail(String email);
}
