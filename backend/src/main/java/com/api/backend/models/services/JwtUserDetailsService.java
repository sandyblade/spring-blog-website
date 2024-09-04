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

package com.api.backend.models.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.api.backend.models.repositories.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private ActivityService activity;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.api.backend.models.entities.User AuthUser = repo.findByEmail(username, 0);
		if (AuthUser != null) {
			if(AuthUser.getConfirmed() == 0) {
				throw new UsernameNotFoundException("You need to confirm your account. We have sent you an activation code, please check your email.");
			}else {
				this.activity.saveActivity(AuthUser, "Sign In", "Sign in to application");
				return new User(AuthUser.getEmail(), AuthUser.getPassword(), new ArrayList<>());
			}
		} else {
			throw new UsernameNotFoundException("User not found with e-mail address: " + username);
		}
	}

}
