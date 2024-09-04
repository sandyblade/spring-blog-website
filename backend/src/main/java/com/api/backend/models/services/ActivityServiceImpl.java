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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.backend.models.entities.Activity;
import com.api.backend.models.repositories.ActivityRepository;

@Service
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityRepository repo;

	@Override
	public Page<Activity> findAll(Pageable pageable, long user_id, String keyword) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable, user_id, keyword);
	}

	@Override
	public Activity saveOrUpdate(Activity model) {
		// TODO Auto-generated method stub
		 this.repo.save(model);
	     return model;
	}

}
