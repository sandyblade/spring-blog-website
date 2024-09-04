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
import com.api.backend.models.entities.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	
	@Query(value="SELECT * FROM public.articles WHERE status = 1 AND (LOWER(x.title) LIKE %?1% OR LOWER(x.description) LIKE %?1% OR LOWER(x.content) LIKE %?1%)", nativeQuery = true)
    Page<Article> findAll(Pageable pageable, String keyword);
	
	@Query(value="SELECT * FROM public.articles WHERE user_id = ?1 AND (LOWER(x.title) LIKE %?2% OR LOWER(x.description) LIKE %?2% OR LOWER(x.content) LIKE %?2%)", nativeQuery = true)
    Page<Article> findByUser(Pageable pageable, long user_id, String keyword);

	@Query(value = "SELECT * from public.articles x WHERE x.slug = ?1 AND x.id <> ?2 LIMIT 1", nativeQuery = true)
	Article findBySlug(String slug, long id);
	
	@Query(value = "SELECT * from public.articles x WHERE x.id = ?1 AND x.user_id = ?2 LIMIT 1", nativeQuery = true)
	Article findById(long id, long user_id);
	
}
