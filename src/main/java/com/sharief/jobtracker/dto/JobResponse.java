package com.sharief.jobtracker.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sharief.jobtracker.entity.JobStatus;

public class JobResponse {

	
	
	private Long id;
    private String companyName;
    private String position;
    private JobStatus status;
    private LocalDate appliedDate;
    private LocalDate interviewDate;
    private String notes;
    private LocalDateTime createdAt;
    
    public JobResponse() {}
    
	public JobResponse(Long id, String companyName, String position, JobStatus status, LocalDate appliedDate,
			LocalDate interviewDate, String notes, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.companyName = companyName;
		this.position = position;
		this.status = status;
		this.appliedDate = appliedDate;
		this.interviewDate = interviewDate;
		this.notes = notes;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public JobStatus getStatus() {
		return status;
	}

	public void setStatus(JobStatus status) {
		this.status = status;
	}

	public LocalDate getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(LocalDate appliedDate) {
		this.appliedDate = appliedDate;
	}

	public LocalDate getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(LocalDate interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    
    
}
