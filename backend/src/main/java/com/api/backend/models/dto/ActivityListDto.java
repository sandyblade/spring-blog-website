package com.api.backend.models.dto;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

public interface ActivityListDto {
	
	public Long getId();
	
	@Value(value = "#{target.event}")
	public String getEvent();
	
	@Value(value = "#{target.description}")
	public String getDescription();

	@Value(value = "#{target.created_at}")
	public Date getCreatedAt();

}
