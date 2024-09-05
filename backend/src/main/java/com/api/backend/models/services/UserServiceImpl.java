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

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.backend.config.EncoderConfig;
import com.api.backend.helpers.CommonHelper;
import com.api.backend.models.dto.UserDetailDto;
import com.api.backend.models.entities.User;
import com.api.backend.models.repositories.UserRepository;
import com.api.backend.models.schema.UserChangePassword;
import com.api.backend.models.schema.UserChangeProfile;

@Service
public class UserServiceImpl implements UserService {
	
    @Autowired
    private UserRepository repo;

	@Override
	public Long TotalRows() {
		return this.repo.count();
	}

	@Override
	public User saveOrUpdate(User model) {
		 repo.save(model);
	     return model;
	}

	@Override
	public User findByEmail(String email, long id) {
		// TODO Auto-generated method stub
		return this.repo.findByEmail(email, id);
	}

	@Override
	public User findByPhone(String phone, long id) {
		// TODO Auto-generated method stub
		return this.repo.findByPhone(phone, id);
	}

	@Override
	public User findByConfirmToken(String token) {
		// TODO Auto-generated method stub
		return this.repo.findByConfirmToken(token);
	}

	@Override
	public User findByResetToken(String token) {
		// TODO Auto-generated method stub
		return this.repo.findByResetToken(token);
	}

	@Override
	public UserDetailDto detail(User user) {
		// TODO Auto-generated method stub
		UserDetailDto model = new UserDetailDto();
		model.setId(user.getId());
		model.setEmail(user.getEmail());
		model.setPhone(user.getPhone());
		model.setImage(user.getImage());
		if(user.getFirstName() != null && user.getLastName() != null){
			model.setFullName(user.getFirstName()+" "+user.getLastName());
		}else{
			model.setFullName(user.getFirstName());
		}
		model.setGender(user.getGender());
		model.setCountry(user.getCountry());
		model.setFacebook(user.getFacebook());
		model.setInstagram(user.getInstagram());
		model.setTwitter(user.getTwitter());
		model.setLinkedIn(user.getLinkedIn());
		model.setAddress(user.getAddress());
		model.setAboutMe(user.getAboutMe());
		model.setCreatedAt(user.getCreatedAt());
		model.setUpdatedAt(user.getUpdatedAt());
		return model;
	}

	@Override
	public User ChangeProfile(User User, UserChangeProfile model) {
		// TODO Auto-generated method stub
		try {
			User.setEmail(model.getEmail());
			User.setPhone(model.getPhone());
			User.setFirstName(model.getFirstName());
			User.setLastName(model.getLastName());
			User.setGender(model.getGender());
			User.setCountry(model.getCountry());
			User.setFacebook(model.getFacebook());
			User.setInstagram(model.getInstagram());
			User.setTwitter(model.getTwitter());
			User.setLinkedIn(model.getLinkedIn());
			User.setAddress(model.getAddress());
			User.setAboutMe(model.getAboutMe());
			User.setUpdatedAt(CommonHelper.DateNow());
			return saveOrUpdate(User);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User ChangePassword(User User, UserChangePassword model) {
		// TODO Auto-generated method stub
		try {
			EncoderConfig enc = new EncoderConfig();
			String password = enc.passwordEncoder().encode(model.getNewPassword());
			User.setPassword(password);
			User.setUpdatedAt(CommonHelper.DateNow());
			return saveOrUpdate(User);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void UploadImage(User User, String Image) {
		// TODO Auto-generated method stub
		try {
			User.setImage(Image);
			User.setUpdatedAt(CommonHelper.DateNow());
			saveOrUpdate(User);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
