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
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.api.backend.helpers.CommonHelper;
import com.api.backend.models.dto.ActivityListDto;
import com.api.backend.models.entities.Activity;
import com.api.backend.models.entities.User;
import com.api.backend.models.repositories.ActivityRepository;

@Service
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityRepository repo;

	

	@Override
	public Activity saveOrUpdate(Activity model) {
		// TODO Auto-generated method stub
		 this.repo.save(model);
	     return model;
	}

	@Override
	public void saveActivity(User user, String Event, String Description) {
		// TODO Auto-generated method stub
		try {
			Activity activity = new Activity();
			activity.setUser(user);
			activity.setEvent(Event);
			activity.setDescription(Description);
			activity.setCreatedAt(CommonHelper.DateNow());
			activity.setUpdatedAt(CommonHelper.DateNow());
			this.repo.save(activity);
		} catch (ParseException e) {
			e.printStackTrace();	
		}
	}

	@Override
	public List<ActivityListDto> findAll(long user_id, int start, int length, String orderby, String orderdir, String search) {
		// TODO Auto-generated method stub
		List<ActivityListDto> result = new ArrayList<ActivityListDto>();
		Pageable paging = PageRequest.of(start, length, Sort.by(orderby.equalsIgnoreCase("asc") ? Sort.Direction.ASC :  Sort.Direction.DESC, orderby));
		Page<ActivityListDto> rows = repo.findAll(paging, user_id, search.toLowerCase());
		result = rows.getContent();
		return result;
	}

}
