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
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.backend.config.EncoderConfig;
import com.api.backend.config.JwtTokenUtil;
import com.api.backend.helpers.CommonHelper;
import com.api.backend.models.services.UserService;
import com.api.backend.models.dto.JsonResponseDto;
import com.api.backend.models.dto.JwtResponseDto;
import com.api.backend.models.dto.UserDetailDto;
import com.api.backend.models.entities.User;
import com.api.backend.models.schema.UserForgotPassword;
import com.api.backend.models.schema.UserLogin;
import com.api.backend.models.schema.UserRegister;
import com.api.backend.models.schema.UserResetPassword;
import com.api.backend.models.services.JwtUserDetailsService;
import com.api.backend.models.services.ActivityService;

@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService UserService;
	
	@Autowired
	private ActivityService ActivityService;


	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(value = "/api/auth/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> Login(@RequestBody UserLogin authenticationRequest) {
		try {

			String username = authenticationRequest.getEmail();
			String password = authenticationRequest.getPassword();

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
			final User user = UserService.findByEmail(username, 0);

			if (user.getConfirmed() == 0) {
				return new ResponseEntity<Object>(new JsonResponseDto(false, "You need to confirm your account. We have sent you an activation code, please check your email.!", null), HttpStatus.BAD_REQUEST);
			} else {
				final String token = jwtTokenUtil.generateToken(userDetails);
				if(!token.isEmpty()){
					// Save Activity
					ActivityService.saveActivity(user, "Sign In", "Sign in to application");
				}
				return ResponseEntity.ok(new JwtResponseDto(token));
			}

		} catch (Exception e) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "These credentials do not match our records.", null), HttpStatus.UNAUTHORIZED);
		}
	}
	

	@RequestMapping(value = "/api/auth/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> Register(@RequestBody UserRegister form) throws ParseException {
		
		if (form.getEmail() == null || form.getEmail().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'email' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}

		if (form.getPassword() == null || form.getPassword().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'password' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}

		if (form.getPasswordConfirm() == null || form.getPasswordConfirm().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'passwordConfirm' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}

		if (form.getPassword().length() < 8) {
			return new ResponseEntity<Object>( new JsonResponseDto(false, "The password must be at least 8 characters.!", null), HttpStatus.BAD_REQUEST);
		}

		if (!form.getPassword().equalsIgnoreCase(form.getPasswordConfirm())) {
			return new ResponseEntity<Object>( new JsonResponseDto(false, "The password confirmation does not match.!", null), HttpStatus.BAD_REQUEST);
		}

		User UserByEmail = UserService.findByEmail(form.getEmail(), 0);

		if (UserByEmail != null) {
			return new ResponseEntity<Object>( new JsonResponseDto(false, "The email address has already been taken.!", null), HttpStatus.BAD_REQUEST);
		}

		EncoderConfig enc = new EncoderConfig();
		String password = enc.passwordEncoder().encode(form.getPassword());
		String token = UUID.randomUUID().toString();

		User NewUser = new User();
		NewUser.setPassword(password);	
		NewUser.setEmail(form.getEmail());
		NewUser.setConfirmed(0);
		NewUser.setConfirmToken(token);
		NewUser.setCreatedAt(CommonHelper.DateNow());
		NewUser.setUpdatedAt(CommonHelper.DateNow());
		UserService.saveOrUpdate(NewUser);
		
		// Save Activity
		ActivityService.saveActivity(NewUser, "Sign Up", "Register new user account");
		
		UserDetailDto UserDetail = UserService.detail(NewUser);

		return new ResponseEntity<Object>(new JsonResponseDto(true, "You need to confirm your account. We have sent you an activation code, please check your email.!", UserDetail), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/auth/confirm/{token}", method = RequestMethod.GET)
	public ResponseEntity<?> Confirm(@PathVariable String token) throws ParseException {

		User user = UserService.findByConfirmToken(token);

		if (user == null) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "These credentials with token do not match our records.!", null), HttpStatus.BAD_REQUEST);
		}

		user.setConfirmed(1);
		user.setUpdatedAt(CommonHelper.DateNow());
		UserService.saveOrUpdate(user);
		
		// Save Activity
		ActivityService.saveActivity(user, "Email Verification", "Confirm new member registration account");

		return new ResponseEntity<Object>(new JsonResponseDto(true, "Your e-mail is verified. You can now login.", user), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/auth/email/forgot", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> ForgotPassword(@RequestBody UserForgotPassword form) throws ParseException {

		if (form.getEmail() == null || form.getEmail().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'email' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}

		User user = UserService.findByEmail(form.getEmail(), 0);

		if (user == null) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "We can't find a user with that e-mail address.", null), HttpStatus.BAD_REQUEST);
		}

		String token = UUID.randomUUID().toString();
		user.setResetToken(token);
		user.setUpdatedAt(CommonHelper.DateNow());
		UserService.saveOrUpdate(user);

		// Save Activity
		ActivityService.saveActivity(user, "Forgot Password", "Request reset password link");
		
		return new ResponseEntity<Object>(new JsonResponseDto(true, "We have e-mailed your password reset link!", token), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/auth/email/reset/{token}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> reset(@PathVariable String token, @RequestBody UserResetPassword form) throws ParseException {

		if (form.getEmail() == null || form.getEmail().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'email' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}

		User user = UserService.findByEmail(form.getEmail(), 0);

		if (user == null) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "We can't find a user with that e-mail address.", null), HttpStatus.BAD_REQUEST);
		}
		
		if (user.getResetToken() == null) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "We can't find a user with token valid.", null), HttpStatus.BAD_REQUEST);
		}

		if (form.getPassword() == null || form.getPassword().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'password' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}

		if (form.getPasswordConfirm() == null || form.getPasswordConfirm().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'passwordConfirm' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}

		if (form.getPassword().length() < 8) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The password must be at least 8 characters.!", null), HttpStatus.BAD_REQUEST);
		}

		if (!form.getPassword().equalsIgnoreCase(form.getPasswordConfirm())) {
			return new ResponseEntity<Object>( new JsonResponseDto(false, "The password confirmation does not match.!", null), HttpStatus.BAD_REQUEST);
		}
		
		if (!user.getResetToken().equalsIgnoreCase(token)) {
			return new ResponseEntity<Object>(new JsonResponseDto(false,"We can't find a user with that e-mail address or password reset token is invalid.", null), HttpStatus.BAD_REQUEST);
		}

		EncoderConfig enc = new EncoderConfig();
		String password = enc.passwordEncoder().encode(form.getPassword());

		user.setPassword(password);
		user.setResetToken(null);
		user.setUpdatedAt(CommonHelper.DateNow());
		UserService.saveOrUpdate(user);
		
		// Save Activity
		ActivityService.saveActivity(user, "Reset Password", "Reset user account password");

		return new ResponseEntity<Object>(new JsonResponseDto(true, "Your password has been reset!", user), HttpStatus.OK);
	}
	

}
