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

import com.api.backend.models.dto.CommentListDto;
import com.api.backend.models.dto.ICommentDto;

public interface CommentService {
	long countByArticle(long article_id);
	List<ICommentDto> findAll(long article_id);
	List<ICommentDto> findByParent(long article_id, long parent_id);
	List<CommentListDto> BuildTree(List<ICommentDto> elements, long parent_id);
}
