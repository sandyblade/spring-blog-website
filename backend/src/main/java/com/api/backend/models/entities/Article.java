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

package com.api.backend.models.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

@Entity
@Table(name = "articles", indexes = { @Index(columnList = "UserId"), @Index(columnList = "Image"),
		@Index(columnList = "Title"), @Index(columnList = "Slug"), @Index(columnList = "Description"),
		@Index(columnList = "Status"), @Index(columnList = "CreatedAt"), @Index(columnList = "UpdatedAt") })
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User User;

	@Column(nullable = true, columnDefinition = "varchar(255)")
	private String Image;

	@Column(nullable = false, columnDefinition = "varchar(255)")
	private String Title;

	@Column(nullable = false, columnDefinition = "varchar(255)")
	private String Slug;

	@Column(nullable = false, columnDefinition = "varchar(255)")
	private String Description;

	@Column(nullable = false, columnDefinition = "text")
	private String Content;

	@Column(nullable = true, columnDefinition = "text")
	private String Categories;

	@Column(nullable = true, columnDefinition = "text")
	private String Tags;

	@ColumnDefault("0")
	@Column(columnDefinition = "int4")
	private int TotalViewer;

	@ColumnDefault("0")
	@Column(columnDefinition = "int4")
	private int TotalComment;

	@ColumnDefault("0")
	@Column(columnDefinition = "int2")
	private int Status;

	@Column(nullable = true)
	private Date CreatedAt;

	@Column(nullable = true)
	private Date UpdatedAt;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getSlug() {
		return Slug;
	}

	public void setSlug(String slug) {
		Slug = slug;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getCategories() {
		return Categories;
	}

	public void setCategories(String categories) {
		Categories = categories;
	}

	public String getTags() {
		return Tags;
	}

	public void setTags(String tags) {
		Tags = tags;
	}

	public int getTotalViewer() {
		return TotalViewer;
	}

	public void setTotalViewer(int totalViewer) {
		TotalViewer = totalViewer;
	}

	public int getTotalComment() {
		return TotalComment;
	}

	public void setTotalComment(int totalComment) {
		TotalComment = totalComment;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public Date getCreatedAt() {
		return CreatedAt;
	}

	public void setCreatedAt(Date createdAt) {
		CreatedAt = createdAt;
	}

	public Date getUpdatedAt() {
		return UpdatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		UpdatedAt = updatedAt;
	}

}
