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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.backend.models.entities.User;
import com.api.backend.models.repositories.UserRepository;

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

}
