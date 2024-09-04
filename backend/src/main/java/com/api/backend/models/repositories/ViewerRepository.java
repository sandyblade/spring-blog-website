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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.backend.models.entities.Viewer;

public interface ViewerRepository extends JpaRepository<Viewer, Long> {
	
	@Query(value = "select count(*) from public.viewers x where x.user_id = ?1 AND x.article_id = ?2", nativeQuery = true)
	long count(long user_id, long article_id);
	
	@Query(value = "select count(*) from public.viewers x where x.article_id = ?1", nativeQuery = true)
	long countByArticle(long article_id);

}
