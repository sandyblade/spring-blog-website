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

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.backend.models.dto.ArticleListDto;
import com.api.backend.models.dto.NotificationListDto;
import com.api.backend.models.entities.Activity;
import com.api.backend.models.entities.Article;
import com.api.backend.models.entities.User;
import com.api.backend.models.schema.ArticleSchema;

public interface ArticleService {
	 List<ArticleListDto> findAll(int start, int length, String orderby, String orderdir, String search);
	 List<ArticleListDto> findAllByUser(long user_id, int start, int length, String orderby, String orderdir, String search);
	 Article findBySlug(String slug, long id);
	 Article findById(long id, long user_id);
	 Article Create(User User, ArticleSchema Model);
	 Article Update(Article Article, ArticleSchema Model);
	 Article saveOrUpdate(Article model);
	 void Remove(long id, long user_id);
}
