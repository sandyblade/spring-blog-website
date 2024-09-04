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

import com.api.backend.models.entities.Notification;
import com.api.backend.models.repositories.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	private NotificationRepository repo;

	@Override
	public Page<Notification> findAll(Pageable pageable, long user_id, String keyword) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable, user_id, keyword);
	}

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

}
