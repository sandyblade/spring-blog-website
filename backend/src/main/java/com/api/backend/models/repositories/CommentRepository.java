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

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.api.backend.models.dto.ICommentDto;
import com.api.backend.models.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	@Query(value = "select c.id, (case when parent_id is null then 0 else parent_id end) parent_id, c.body, c.created_at, u.first_name, u.last_name, u.gender, u.email from comments c inner join users u on u.id = c.user_id where c.article_id = ?1 order by c.id desc", nativeQuery = true)
	List<ICommentDto> findAll(long article_id);

	@Query(value = "select c.id, (case when parent_id is null then 0 else parent_id end) parent_id, c.body, c.created_at, u.first_name, u.last_name, u.gender, u.email from comments c inner join users u on u.id = c.user_id where c.article_id = ?1 and c.parent_id = ?2 order by c.id desc", nativeQuery = true)
	List<ICommentDto> findByParent(long article_id, long parent_id);

	@Query(value = "SELECT * from public.comments x WHERE x.id = ?1 AND x.user_id = ?2 LIMIT 1", nativeQuery = true)
	Comment findById(long id, long user_id);
	
	@Query(value = "select count(*) from public.comments x where x.article_id = ?1", nativeQuery = true)
	long countByArticle(long article_id);
}
