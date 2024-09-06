package com.api.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.backend.models.dto.JsonResponseDto;
import com.api.backend.models.entities.Article;
import com.api.backend.models.entities.User;
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
	private ArticleService ArticleService;

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
		
		

		CommentService.saveOrUpdate(AuthUser, Artcile, null, null);
		

		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", null), HttpStatus.OK);

	}

}
