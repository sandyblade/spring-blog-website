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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.api.backend.models.dto.NotificationListDto;
import com.api.backend.models.entities.Notification;
import com.api.backend.models.entities.User;
import com.api.backend.models.repositories.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	private NotificationRepository repo;

	@Override
	public Notification findById(long id, long user_id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id, user_id);
	}

	@Override
	public Notification saveOrUpdate(Notification model) {
		// TODO Auto-generated method stub
		 this.repo.save(model);
	     return model;
	}

	@Override
	public void Remove(long id, long user_id) {
		// TODO Auto-generated method stub
		Notification model = this.repo.findById(id, user_id);
		this.repo.delete(model); 
	}

	@Override
	public void sendNotif(User user, String Subject, String Message) {
		// TODO Auto-generated method stub
		Notification notif = new Notification();
		notif.setUser(user);
		notif.setSubject(Subject);
		notif.setMessage(Message);
		this.repo.save(notif);
	}

	@Override
	public List<NotificationListDto> findAll(long user_id, int start, int length, String orderby, String orderdir, String search) {
		// TODO Auto-generated method stub
		List<NotificationListDto> result = new ArrayList<NotificationListDto>();
		Pageable paging = PageRequest.of(start, length, Sort.by(orderby.equalsIgnoreCase("asc") ? Sort.Direction.ASC :  Sort.Direction.DESC, orderby));
		Page<NotificationListDto> rows = repo.findAll(paging, user_id, search.toLowerCase());
		result = rows.getContent();
		return result;
	}

}
