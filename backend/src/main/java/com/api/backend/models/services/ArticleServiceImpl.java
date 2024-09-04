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
import com.api.backend.models.entities.Article;
import com.api.backend.models.repositories.ArticleRepository;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository repo;

	@Override
	public Page<Article> findAll(Pageable pageable, String keyword) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable, keyword);
	}

	@Override
	public Page<Article> findByUser(Pageable pageable, long user_id, String keyword) {
		// TODO Auto-generated method stub
		return this.repo.findByUser(pageable, user_id, keyword);
	}

	@Override
	public Article findBySlug(String slug, long id) {
		// TODO Auto-generated method stub
		return this.repo.findBySlug(slug, id);
	}

	@Override
	public Article findById(long id, long user_id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id, user_id);
	}

	@Override
	public Article saveOrUpdate(Article model) {
		// TODO Auto-generated method stub
		this.repo.save(model);
		return model;
	}

	@Override
	public void Remove(long id, long user_id) {
		Article model = this.repo.findById(id, user_id);
		this.repo.delete(model);

	}

}
