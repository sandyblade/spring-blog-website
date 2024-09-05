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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.backend.config.JwtTokenUtil;
import com.api.backend.helpers.CommonHelper;
import com.api.backend.models.dto.ActivityListDto;
import com.api.backend.models.dto.JsonResponseDto;
import com.api.backend.models.dto.JwtResponseDto;
import com.api.backend.models.dto.UserDetailDto;
import com.api.backend.models.entities.Activity;
import com.api.backend.models.entities.User;
import com.api.backend.models.schema.FormUploadFile;
import com.api.backend.models.schema.UserChangePassword;
import com.api.backend.models.schema.UserChangeProfile;
import com.api.backend.models.schema.UserLogin;
import com.api.backend.models.services.ActivityService;
import com.api.backend.models.services.FileStorageService;
import com.api.backend.models.services.JwtUserDetailsService;
import com.api.backend.models.services.UserService;

@RestController
public class AccountController extends BaseController {
	
	@Autowired
	private UserService UserService;
	
	@Autowired
	private ActivityService ActivityService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private FileStorageService fileStorageService;

	@RequestMapping(value = "/api/account/detail", method = RequestMethod.GET)
	public ResponseEntity<?> Detail() {
		User AuthUser = this.AuthUser();
		UserDetailDto UserDetail = UserService.detail(AuthUser);
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", UserDetail), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/account/token", method = RequestMethod.POST)
	public ResponseEntity<?> Token() {
		User AuthUser = this.AuthUser();
		final UserDetails userDetails = userDetailsService.loadUserByUsername(AuthUser.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponseDto(token));
	}
	

	@RequestMapping(value = "/api/account/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> Update(@RequestBody UserChangeProfile model) {
		
		User AuthUser = this.AuthUser();
		User CheckEmail = UserService.findByEmail(model.getEmail(), AuthUser.getId());
		User CheckPhone = UserService.findByPhone(model.getPhone(), AuthUser.getId());
		
		if (model.getEmail() == null || model.getEmail().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'email' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}
		
		if (model.getPhone() == null || model.getPhone().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'phone' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}
		
		if(CheckEmail != null) {
			return new ResponseEntity<Object>( new JsonResponseDto(false, "The e-mail address has already been taken.!", null), HttpStatus.BAD_REQUEST);
		}
		
		if(CheckPhone != null) {
			return new ResponseEntity<Object>( new JsonResponseDto(false, "The phone number has already been taken.!", null), HttpStatus.BAD_REQUEST);
		}
		
		User UpdateUser = UserService.ChangeProfile(AuthUser, model);
		UserDetailDto UserDetail = UserService.detail(UpdateUser);
		
		// Save Activity
		ActivityService.saveActivity(UpdateUser, "Update Profile", "Edit user profile account");
		
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", UserDetail), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/account/password", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> Password(@RequestBody UserChangePassword model) {
		
		User AuthUser = this.AuthUser();
		
		if (model.getOldPassword() == null || model.getOldPassword().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'oldPassword' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}

		if (model.getNewPassword() == null || model.getNewPassword().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'newPassword' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}

		if (model.getPasswordConfirm() == null || model.getPasswordConfirm().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'passwordConfirm' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}

		if (model.getNewPassword().length() < 8) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The password must be at least 8 characters.!", null), HttpStatus.BAD_REQUEST);
		}

		if (!model.getNewPassword().equalsIgnoreCase(model.getPasswordConfirm())) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The password confirmation does not match.!", null), HttpStatus.BAD_REQUEST);
		}

		boolean matches = passwordEncoder.matches(model.getOldPassword(), AuthUser.getPassword());
		
		if (!matches) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The current password does not match!", null), HttpStatus.BAD_REQUEST);
		}
		
		User UpdateUser = UserService.ChangePassword(AuthUser, model);
		UserDetailDto UserDetail = UserService.detail(UpdateUser);
		
		// Save Activity
		ActivityService.saveActivity(UpdateUser, "Change Password", "Change new password account");
		
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", UserDetail), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/api/account/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> Upload(@RequestParam("file") MultipartFile file) {
		
		User user = this.AuthUser();
		String fileName = fileStorageService.storeFile(file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/page/file/").path(fileName).toUriString();
		FormUploadFile result = new FormUploadFile(fileName, fileDownloadUri, file.getContentType(), file.getSize());
		
		if (result.getFileDownloadUri() != null) {
			UserService.UploadImage(user, fileName);
		}
		
		UserDetailDto UserDetail = UserService.detail(user);
		
		// Save Activity
		ActivityService.saveActivity(user, "Upload Image", "Upload new user profile image");
		
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", UserDetail), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/api/account/activity", method = RequestMethod.GET)
	public ResponseEntity<?> Activity(
			@RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "10") int limit, 
			@RequestParam(defaultValue = "id") String orderby,
			@RequestParam(defaultValue = "desc") String orderdir,
			@RequestParam(defaultValue = "") String search
		) {
		User user = this.AuthUser();
		int offset = ((page-1)*limit);
		List<ActivityListDto> payload = ActivityService.findAll(user.getId(), offset, limit, orderby, orderdir, search);
		System.out.println(payload.size());
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}
	
}
