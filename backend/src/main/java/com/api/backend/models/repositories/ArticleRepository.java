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

package com.api.backend.models.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.backend.models.dto.ActivityListDto;
import com.api.backend.models.dto.ArticleListDto;
import com.api.backend.models.entities.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

	@Query(value = "SELECT x.id, x.title, x.slug, x.description, x.categories, x.tags, x.created_at, x.updated_at, u.first_name, u.last_name, u.gender, u.email, u.image FROM public.articles x INNER JOIN public.users u ON u.id = x.user_id WHERE x.status = 1 AND( LOWER(CAST(x.title as varchar)) LIKE %?1% OR LOWER(CAST(x.slug as varchar)) LIKE %?1% OR LOWER(CAST(x.description as varchar)) LIKE %?1% OR LOWER(CAST(x.tags as varchar)) LIKE %?1% OR LOWER(CAST(x.categories as varchar)) LIKE %?1% OR LOWER(CAST(u.first_name as varchar)) LIKE %?1% OR LOWER(CAST(u.last_name as varchar)) LIKE %?1% OR LOWER(CAST(u.gender as varchar)) LIKE %?1% OR LOWER(CAST(u.email as varchar)) LIKE %?1%)", nativeQuery = true)
	Page<ArticleListDto> findAll(Pageable pageable, String keyword);
	
	@Query(value = "SELECT x.id, x.title, x.slug, x.description, x.categories, x.tags, x.created_at, x.updated_at, u.first_name, u.last_name, u.gender, u.email, u.image FROM public.articles x INNER JOIN public.users u ON u.id = x.user_id WHERE x.user_id = ?1 AND( LOWER(CAST(x.title as varchar)) LIKE %?2% OR LOWER(CAST(x.slug as varchar)) LIKE %?2% OR LOWER(CAST(x.description as varchar)) LIKE %?2% OR LOWER(CAST(x.tags as varchar)) LIKE %?2% OR LOWER(CAST(x.categories as varchar)) LIKE %?2% OR LOWER(CAST(u.first_name as varchar)) LIKE %?2% OR LOWER(CAST(u.last_name as varchar)) LIKE %?2% OR LOWER(CAST(u.gender as varchar)) LIKE %?2% OR LOWER(CAST(u.email as varchar)) LIKE %?2%)", nativeQuery = true)
	Page<ArticleListDto> findAllByUser(Pageable pageable, long user_id, String keyword);

	@Query(value = "SELECT * from public.articles x WHERE x.slug = ?1 AND x.id <> ?2 LIMIT 1", nativeQuery = true)
	Article findBySlug(String slug, long id);

	@Query(value = "SELECT * from public.articles x WHERE x.id = ?1 AND x.user_id = ?2 LIMIT 1", nativeQuery = true)
	Article findById(long id, long user_id);

}
