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

package com.api.backend.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.backend.helpers.CommonHelper;
import com.api.backend.models.dto.ArticleListDto;
import com.api.backend.models.dto.JsonResponseDto;
import com.api.backend.models.dto.NotificationListDto;
import com.api.backend.models.dto.UserDetailDto;
import com.api.backend.models.entities.Article;
import com.api.backend.models.entities.Notification;
import com.api.backend.models.entities.User;
import com.api.backend.models.entities.Viewer;
import com.api.backend.models.schema.ArticleSchema;
import com.api.backend.models.schema.FormUploadFile;
import com.api.backend.models.services.ActivityService;
import com.api.backend.models.services.ArticleService;
import com.api.backend.models.services.FileStorageService;
import com.api.backend.models.services.ViewerService;
import com.github.javafaker.Faker;

import io.jsonwebtoken.lang.Arrays;

@RestController
public class ArticleController extends BaseController {
	
	@Autowired
	private ActivityService ActivityService;
	
	@Autowired
	private ArticleService ArticleService;
	
	@Autowired
	private ViewerService ViewerService;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@RequestMapping(value = "/api/article/list", method = RequestMethod.GET)
	public ResponseEntity<?> List(
			@RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "10") int limit, 
			@RequestParam(defaultValue = "id") String orderby,
			@RequestParam(defaultValue = "desc") String orderdir,
			@RequestParam(defaultValue = "") String search
		) {
		int offset = ((page-1)*limit);
		List<ArticleListDto> payload = ArticleService.findAll(offset, limit, orderby, orderdir, search);
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/article/user", method = RequestMethod.GET)
	public ResponseEntity<?> User(
			@RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "10") int limit, 
			@RequestParam(defaultValue = "id") String orderby,
			@RequestParam(defaultValue = "desc") String orderdir,
			@RequestParam(defaultValue = "") String search
		) {
		User user = this.AuthUser();
		int offset = ((page-1)*limit);
		List<ArticleListDto> payload = ArticleService.findAllByUser(user.getId(), offset, limit, orderby, orderdir, search);
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/article/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> Create(@RequestBody ArticleSchema model) {
		
		User AuthUser = this.AuthUser();
		
		if (model.getTitle() == null || model.getTitle().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'title' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}
		
		if (model.getDescription() == null || model.getDescription().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'description' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}
		
		if (model.getContent() == null || model.getContent().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'content' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}
		
		Article CheckTitle = ArticleService.findBySlug(CommonHelper.slugify(model.getTitle()), 0);
		
		if(CheckTitle != null) {
			return new ResponseEntity<Object>( new JsonResponseDto(false, "The article with title `"+model.getTitle()+"` has already been taken.!", null), HttpStatus.BAD_REQUEST);
		}
		
		// Save Activity
		ActivityService.saveActivity(AuthUser, "Create New Article", "A new article with title `"+model.getTitle()+"` has been created. ");
		
		Article NewArticle = ArticleService.Create(AuthUser, model);
		HashMap<String, Object> payload = new HashMap<>();
		payload.put("id", NewArticle.getId());
		payload.put("title", NewArticle.getTitle());
		payload.put("slug", NewArticle.getSlug());
		payload.put("description", NewArticle.getDescription());
		payload.put("content", NewArticle.getContent());
		payload.put("categories", model.getCategories());
		payload.put("tags", model.getTags());
		payload.put("status", model.getStatus());
		payload.put("createdAt", NewArticle.getCreatedAt());
		payload.put("updatedAt", NewArticle.getCreatedAt());

		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/api/article/update/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> Update(@PathVariable(value = "id") Long Id, @RequestBody ArticleSchema model) {
		
		User AuthUser = this.AuthUser();
		Article Article = ArticleService.findById(Id, AuthUser.getId());
		
		if(Article == null) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The article with id "+Id+" was not found in our record !!", null), HttpStatus.BAD_REQUEST);
		}
		
		if (model.getTitle() == null || model.getTitle().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'title' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}
		
		if (model.getDescription() == null || model.getDescription().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'description' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}
		
		if (model.getContent() == null || model.getContent().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'content' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}
		
		Article CheckTitle = ArticleService.findBySlug(CommonHelper.slugify(model.getTitle()), Id);
		
		if(CheckTitle != null) {
			return new ResponseEntity<Object>( new JsonResponseDto(false, "The article with title `"+model.getTitle()+"` has already been taken.!", null), HttpStatus.BAD_REQUEST);
		}
		
		// Save Activity
		ActivityService.saveActivity(AuthUser, "Edit Article", "An a article with title `"+model.getTitle()+"` has been updated.");
		
		Article NewArticle = ArticleService.Update(Article, model);
		HashMap<String, Object> payload = new HashMap<>();
		payload.put("id", NewArticle.getId());
		payload.put("title", NewArticle.getTitle());
		payload.put("slug", NewArticle.getSlug());
		payload.put("description", NewArticle.getDescription());
		payload.put("content", NewArticle.getContent());
		payload.put("categories", model.getCategories());
		payload.put("tags", model.getTags());
		payload.put("status", model.getStatus());
		payload.put("createdAt", NewArticle.getCreatedAt());
		payload.put("updatedAt", NewArticle.getCreatedAt());

		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/api/article/remove/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> Remove(@PathVariable(value = "id") Long Id) throws ParseException {

		User AuthUser = this.AuthUser();
		Article Article = ArticleService.findById(Id, AuthUser.getId());
		
		if(Article == null) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The article with id "+Id+" was not found in our record !!", null), HttpStatus.BAD_REQUEST);
		}
		
		ArticleService.Remove(Id, AuthUser.getId());
		
		// Save Activity
		ActivityService.saveActivity(AuthUser, "Delete Article", "An a article with title `"+Article.getTitle()+"` has been removed.");
		
		Article NewArticle = Article;
		HashMap<String, Object> payload = new HashMap<>();
		payload.put("image", NewArticle.getImage());
		payload.put("id", NewArticle.getId());
		payload.put("title", NewArticle.getTitle());
		payload.put("slug", NewArticle.getSlug());
		payload.put("description", NewArticle.getDescription());
		payload.put("content", NewArticle.getContent());
		payload.put("categories", NewArticle.getCategories().split("\\,", -1));
		payload.put("tags", NewArticle.getTags().split("\\,", -1));
		payload.put("status", NewArticle.getStatus());
		payload.put("createdAt", NewArticle.getCreatedAt());
		payload.put("updatedAt", NewArticle.getCreatedAt());

		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/article/read/{slug}", method = RequestMethod.GET)
	public ResponseEntity<?> Read(@PathVariable(value = "slug") String slug) throws ParseException {

		User AuthUser = this.AuthUser();
		Article Article = ArticleService.findBySlug(slug, 0);
		
		if(Article == null) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The article with slug "+slug+" was not found in our record !!", null), HttpStatus.BAD_REQUEST);
		}
		
		if(this.AuthUser() != null) {
			if(this.AuthUser() != Article.getUser()) {
				long Viewer = ViewerService.count(AuthUser.getId(), Article.getId());
				if(Viewer == 0) {
					
					Viewer viewer = new Viewer();
					viewer.setArticle(Article);
					viewer.setUser(AuthUser);
					viewer.setStatus(0);
					ViewerService.saveOrUpdate(viewer);
					
					int TotalViewerService = (int) ViewerService.countByArticle(Article.getId());
					Article.setTotalViewer(TotalViewerService);
					ArticleService.saveOrUpdate(Article);
					
				}
			}
		}
			
		// Save Activity
		// ActivityService.saveActivity(AuthUser, "Delete Article", "An a article with title `"+Article.getTitle()+"` has been removed.");
		
		HashMap<String, Object> user = new HashMap<>();
		user.put("first_name", Article.getUser().getFirstName());
		user.put("last_name", Article.getUser().getLastName());
		user.put("gender", Article.getUser().getGender());
		user.put("email", Article.getUser().getEmail());
		
		Article NewArticle = Article;
		HashMap<String, Object> payload = new HashMap<>();
		payload.put("image", NewArticle.getImage());
		payload.put("id", NewArticle.getId());
		payload.put("title", NewArticle.getTitle());
		payload.put("slug", NewArticle.getSlug());
		payload.put("description", NewArticle.getDescription());
		payload.put("content", NewArticle.getContent());
		payload.put("categories", NewArticle.getCategories().split("\\,", -1));
		payload.put("tags", NewArticle.getTags().split("\\,", -1));
		payload.put("status", NewArticle.getStatus());
		payload.put("createdAt", NewArticle.getCreatedAt());
		payload.put("updatedAt", NewArticle.getCreatedAt());
		payload.put("user", user);
		
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/article/upload/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> Upload(@PathVariable(value = "id") Long Id, @RequestParam("file") MultipartFile file) {
		
		User AuthUser = this.AuthUser();
		Article Article = ArticleService.findById(Id, AuthUser.getId());
		
		if(Article == null) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The article with id "+Id+" was not found in our record !!", null), HttpStatus.BAD_REQUEST);
		}
		
		String fileName = fileStorageService.storeFile(file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/page/file/").path(fileName).toUriString();
		FormUploadFile result = new FormUploadFile(fileName, fileDownloadUri, file.getContentType(), file.getSize());
		
		if (result.getFileDownloadUri() != null) {
			Article.setImage(fileName);
			ArticleService.saveOrUpdate(Article);
			// Save Activity
			ActivityService.saveActivity(AuthUser, "Upload Image Article", "An a article with title `"+Article.getTitle()+"` has been uploaded image.");
		}

		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", fileName), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/api/article/words", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> Word(@RequestParam(defaultValue = "10") int max) {
		List<String> payload = new ArrayList<String>();
		for(int i = 1; i <= max; i++) {
			Faker faker = new Faker();
			String Word = faker.lorem().word()+" "+faker.lorem().word();
			String cap = Word.substring(0, 1).toUpperCase() + Word.substring(1);
			payload.add(cap);
		}
		Collections.sort(payload);
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}
	

	
}
