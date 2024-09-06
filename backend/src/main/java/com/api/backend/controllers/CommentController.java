package com.api.backend.controllers;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.backend.models.dto.CommentListDto;
import com.api.backend.models.dto.ICommentDto;
import com.api.backend.models.dto.JsonResponseDto;
import com.api.backend.models.entities.Article;
import com.api.backend.models.entities.User;
import com.api.backend.models.entities.Comment;
import com.api.backend.models.schema.ArticleCommentSchema;
import com.api.backend.models.schema.ArticleSchema;
import com.api.backend.models.services.ActivityService;
import com.api.backend.models.services.NotificationService;
import com.api.backend.models.services.CommentService;
import com.api.backend.models.services.ArticleService;

@RestController
public class CommentController extends BaseController {

	@Autowired
	private CommentService CommentService;

	@Autowired
	private NotificationService NotificationService;
	
	@Autowired
	private ActivityService ActivityService;

	@Autowired
	private ArticleService ArticleService;
	
	@RequestMapping(value = "/api/comment/list/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> List(@PathVariable(value = "id") Long Id)  {
		
		Article Artcile = ArticleService.find(Id);

		if (Artcile == null) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The article with id " + Id + " was not found in our record !!", null), HttpStatus.BAD_REQUEST);
		}
		
		List<ICommentDto> comments = CommentService.findAll(Id);
		List<CommentListDto> payload = CommentService.BuildTree(comments, 0);
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}
	

	@RequestMapping(value = "/api/comment/create/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> Create(@PathVariable(value = "id") Long Id, @RequestBody ArticleCommentSchema model) {

		User AuthUser = this.AuthUser();

		if (model.getBody() == null || model.getBody().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'body' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}

		Article Artcile = ArticleService.find(Id);

		if (Artcile == null) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The article with id " + Id + " was not found in our record !!", null), HttpStatus.BAD_REQUEST);
		}
		
		Comment parent = CommentService.findBy(model.getParentId());
		Comment comment = CommentService.saveOrUpdate(AuthUser, Artcile, model.getBody(), parent);
		
		String Event = parent == null ? "Create Comment" : "Reply Comment";
		String Action = parent == null ? "added" : "replied";
		
		// Save Activity
		ActivityService.saveActivity(AuthUser, Event, "A an article with title `"+Artcile.getTitle()+"` has been "+Action+". ");
		
		if(Artcile.getUser() != AuthUser) {
			// Send Notification
			NotificationService.sendNotif(Artcile.getUser(), Event, Action);
		}
		
		
		HashMap<String, Object> payload = new HashMap<>();
		payload.put("id", comment.getId());
		payload.put("body", comment.getBody());
		payload.put("createdAt", comment.getCreatedAt());

		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);

	}
	
	@RequestMapping(value = "/api/comment/remove/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> Remove(@PathVariable(value = "id") Long Id)  {
		
		User AuthUser = this.AuthUser();
		
		Comment Comment = CommentService.findBy(Id);
		
		if(Comment == null) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The comment with id " + Id + " was not found in our record !!", null), HttpStatus.BAD_REQUEST);
		}
		
		if(Comment.getUser() != AuthUser) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The comment with id " + Id + " was not found in our record !!", null), HttpStatus.BAD_REQUEST);
		}
		
		Article Article = Comment.getArticle();
		
		CommentService.Remove(AuthUser, Id);
		
		// Save Activity
		ActivityService.saveActivity(AuthUser, "Delete Comment", "An a comment of article with title `"+Article.getTitle()+"` has been deleted.");
		
		HashMap<String, Object> payload = new HashMap<>();
		payload.put("id", Comment.getId());
		payload.put("body", Comment.getBody());
		payload.put("createdAt", Comment.getCreatedAt());

		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
		
	}
	
	

}
