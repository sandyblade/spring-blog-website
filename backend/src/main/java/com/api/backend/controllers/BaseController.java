package com.api.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.api.backend.models.services.UserService;

public class BaseController {

	@Autowired
	private UserService UserService;
	
	protected com.api.backend.models.entities.User AuthUser(){
		
		if(SecurityContextHolder.getContext().getAuthentication() == null)
		{
			return null;
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return UserService.findByEmail(auth.getName(), 0);
	}

}
