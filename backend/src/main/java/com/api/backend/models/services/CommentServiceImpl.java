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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.backend.helpers.CommonHelper;
import com.api.backend.models.dto.CommentListDto;
import com.api.backend.models.dto.ICommentDto;
import com.api.backend.models.entities.Article;
import com.api.backend.models.entities.Comment;
import com.api.backend.models.entities.User;
import com.api.backend.models.repositories.ArticleRepository;
import com.api.backend.models.repositories.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository repo;

	@Autowired
	private ArticleRepository article;
	
	@Override
	public long countByArticle(long article_id) {
		// TODO Auto-generated method stub
		return this.repo.countByArticle(article_id);
	}

	@Override
	public List<ICommentDto> findAll(long article_id) {
		// TODO Auto-generated method stub
		return this.repo.findAll(article_id);
	}

	@Override
	public List<ICommentDto> findByParent(long article_id, long parent_id) {
		// TODO Auto-generated method stub
		return this.repo.findByParent(article_id, parent_id);
	}

	@Override
	public List<CommentListDto> BuildTree(List<ICommentDto> elements, long parent_id) {
		// TODO Auto-generated method stub
		List<CommentListDto> branch = new ArrayList<CommentListDto>();

		for (ICommentDto element : elements) {
			if (element.getParentId() == parent_id) {
				List<CommentListDto> Children = BuildTree(elements, element.getId());
				CommentListDto tree = new CommentListDto();
				tree.setId(element.getId());
				tree.setParentId(element.getParentId());
				tree.setBody(element.getBody());
				tree.setCreatedAt(element.getCreatedAt());
				tree.setFirstName(element.getFirstName());
				tree.setLastName(element.getLastName());
				tree.setGender(element.getGender());
				tree.setChildren(Children);
				branch.add(tree);
			}
		}

		return branch;
	}

	
	@Override
	public void Remove(User User, long Id) {
		// TODO Auto-generated method stub
		Comment comment = this.repo.findById(Id, User.getId());
		if(comment != null) {
			Article Article = comment.getArticle(); 
			this.repo.delete(comment);
			int total = (int) this.repo.countByArticle(Article.getId());
			Article.setTotalComment(total);
			article.save(Article);
		}
	}

	@Override
	public Comment saveOrUpdate(User User, Article Article, String Body, Comment Parent) {
		// TODO Auto-generated method stub
		try {
			Comment comment = new Comment();
			comment.setUser(User);
			comment.setArticle(Article);
			comment.setBody(Body);
			comment.setParent(Parent);
			comment.setCreatedAt(CommonHelper.DateNow());
			comment.setUpdatedAt(CommonHelper.DateNow());
			comment = this.repo.save(comment);
			
			int total = (int) this.repo.countByArticle(Article.getId());
			Article.setTotalComment(total);
			article.save(Article);
			
			return comment;
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public Comment findBy(long id) {
		// TODO Auto-generated method stub
		return this.repo.findBy(id);
	}

}
