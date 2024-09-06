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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.api.backend.helpers.CommonHelper;
import com.api.backend.models.dto.ArticleListDto;
import com.api.backend.models.dto.NotificationListDto;
import com.api.backend.models.entities.Activity;
import com.api.backend.models.entities.Article;
import com.api.backend.models.entities.User;
import com.api.backend.models.repositories.ArticleRepository;
import com.api.backend.models.schema.ArticleSchema;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository repo;

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
	public Article Create(User User, ArticleSchema Model) {
		// TODO Auto-generated method stub
		try {
			Article Article = new Article();
			Article.setUser(User);
			Article.setTitle(Model.getTitle());
			Article.setSlug(CommonHelper.slugify(Model.getTitle()));
			Article.setDescription(Model.getDescription());
			Article.setContent(Model.getContent());
			Article.setStatus(Model.getStatus());

			if (Model.getCategories().size() > 0) {
				String Categories = StringUtils.join(Model.getCategories(), ",");
				Article.setCategories(Categories);
			}

			if (Model.getTags().size() > 0) {
				String Tags = StringUtils.join(Model.getTags(), ",");
				Article.setTags(Tags);
			}

			Article.setCreatedAt(CommonHelper.DateNow());
			Article.setUpdatedAt(CommonHelper.DateNow());

			this.repo.save(Article);
			return Article;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Article Update(Article Article, ArticleSchema Model) {
		// TODO Auto-generated method stub
		try {
			Article.setTitle(Model.getTitle());
			Article.setSlug(CommonHelper.slugify(Model.getTitle()));
			Article.setDescription(Model.getDescription());
			Article.setContent(Model.getContent());
			Article.setStatus(Model.getStatus());

			if (Model.getCategories().size() > 0) {
				String Categories = StringUtils.join(Model.getCategories(), ",");
				Article.setCategories(Categories);
			}

			if (Model.getTags().size() > 0) {
				String Tags = StringUtils.join(Model.getTags(), ",");
				Article.setTags(Tags);
			}

			Article.setCreatedAt(CommonHelper.DateNow());
			Article.setUpdatedAt(CommonHelper.DateNow());

			this.repo.save(Article);
			return Article;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void Remove(long id, long user_id) {
		// TODO Auto-generated method stub
		Article Article = repo.findById(id, user_id);
		if (Article != null) {
			this.repo.delete(Article);
		}
	}

	@Override
	public Article saveOrUpdate(Article model) {
		// TODO Auto-generated method stub
		return this.repo.save(model);
	}

	@Override
	public List<ArticleListDto> findAll(int start, int length, String orderby, String orderdir, String search) {
		// TODO Auto-generated method stub
		List<ArticleListDto> result = new ArrayList<ArticleListDto>();
		Pageable paging = PageRequest.of(start, length, Sort.by(orderby.equalsIgnoreCase("asc") ? Sort.Direction.ASC :  Sort.Direction.DESC, orderby));
		Page<ArticleListDto> rows = repo.findAll(paging, search.toLowerCase());
		result = rows.getContent();
		return result;
	}

	@Override
	public List<ArticleListDto> findAllByUser(long user_id, int start, int length, String orderby, String orderdir, String search) {
		// TODO Auto-generated method stub
		List<ArticleListDto> result = new ArrayList<ArticleListDto>();
		Pageable paging = PageRequest.of(start, length, Sort.by(orderby.equalsIgnoreCase("asc") ? Sort.Direction.ASC :  Sort.Direction.DESC, orderby));
		Page<ArticleListDto> rows = repo.findAllByUser(paging, user_id, search);
		result = rows.getContent();
		return result;
	}

	@Override
	public Article find(long id) {
		// TODO Auto-generated method stub
		return this.repo.find(id);
	}

}
