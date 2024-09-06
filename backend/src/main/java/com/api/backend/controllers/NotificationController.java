/**
 * This file is part of the Sandy Andryanto Blog Application.
 *
 * @author     Sandy Andryanto <sandy.andryanto.blade@gmail.com>
 * @copyright  2024
 *
 * For the full copyright and license information,
 * please view the LICENSE.md file that was distributed
 * with this source code.
 */

package com.api.backend.controllers;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.backend.models.dto.JsonResponseDto;
import com.api.backend.models.dto.NotificationListDto;
import com.api.backend.models.entities.Notification;
import com.api.backend.models.entities.User;
import com.api.backend.models.services.ActivityService;
import com.api.backend.models.services.NotificationService;

@RestController
public class NotificationController extends BaseController {

	@Autowired
	private ActivityService ActivityService;
	
	@Autowired
	private NotificationService NotificationService;
	
	@RequestMapping(value = "/api/notification/list", method = RequestMethod.GET)
	public ResponseEntity<?> List(
			@RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "10") int limit, 
			@RequestParam(defaultValue = "id") String orderby,
			@RequestParam(defaultValue = "desc") String orderdir,
			@RequestParam(defaultValue = "") String search
		) {
		User user = this.AuthUser();
		int offset = ((page-1)*limit);
		List<NotificationListDto> payload = NotificationService.findAll(user.getId(), offset, limit, orderby, orderdir, search);
		System.out.println(payload.size());
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/notification/read/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> Read(@PathVariable(value = "id") Long Id) throws ParseException {

		User user = this.AuthUser();
		Notification notification = NotificationService.findById(Id, user.getId());

		if (notification == null) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The record with id "+Id+" was not found in our record !!", null), HttpStatus.BAD_REQUEST);
		}

		HashMap<String, Object> payload = new HashMap<>();
		payload.put("id", notification.getId());
		payload.put("subject", notification.getSubject());
		payload.put("message", notification.getMessage());
		payload.put("createdAt", notification.getCreatedAt());

		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/notification/remove/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> Remove(@PathVariable(value = "id") Long Id) throws ParseException {

		User user = this.AuthUser();
		Notification notification = NotificationService.findById(Id, user.getId());

		if (notification == null) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The record with id "+Id+" was not found in our record !!", null), HttpStatus.BAD_REQUEST);
		}

		HashMap<String, Object> payload = new HashMap<>();
		payload.put("id", notification.getId());
		payload.put("subject", notification.getSubject());
		payload.put("message", notification.getMessage());
		payload.put("createdAt", notification.getCreatedAt());
		
		NotificationService.Remove(Id, user.getId());
		
		// Save Activity
		ActivityService.saveActivity(user, "Delete Notification", "The user delete notification with subject "+notification.getSubject());

		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}

}
