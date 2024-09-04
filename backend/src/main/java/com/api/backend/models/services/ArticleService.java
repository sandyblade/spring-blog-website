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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.api.backend.models.entities.Article;

public interface ArticleService {
	 Page<Article> findAll(Pageable pageable, String keyword);
	 Page<Article> findByUser(Pageable pageable, long user_id, String keyword);
	 Article findBySlug(String slug, long id);
	 Article findById(long id, long user_id);
	 Article saveOrUpdate(Article model);
	 void Remove(long id, long user_id);
}
