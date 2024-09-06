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

import com.api.backend.helpers.CommonHelper;
import com.api.backend.models.entities.Viewer;
import com.api.backend.models.repositories.ViewerRepository;

@Service
public class ViewerServiceImpl implements ViewerService {
	
	@Autowired
	private ViewerRepository repo;

	@Override
	public long count(long user_id, long article_id) {
		// TODO Auto-generated method stub
		return repo.count(user_id, article_id);
	}

	@Override
	public Viewer saveOrUpdate(Viewer model) {
		// TODO Auto-generated method stub
		try {
			model.setCreatedAt(CommonHelper.DateNow());
			model.setUpdatedAt(CommonHelper.DateNow());
			this.repo.save(model);
		    return model;
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public long countByArticle(long article_id) {
		// TODO Auto-generated method stub
		return this.repo.countByArticle(article_id);
	}

}
