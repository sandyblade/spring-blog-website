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

package com.api.backend.models.dto;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

public interface ArticleListDto {

	public Long getId();

	@Value(value = "#{target.title}")
	public String getTitle();

	@Value(value = "#{target.slug}")
	public String getSlug();
	
	@Value(value = "#{target.description}")
	public String getDescription();
	
	@Value(value = "#{target.categories}")
	public String getCategories();
	
	@Value(value = "#{target.tags}")
	public String getTags();

	@Value(value = "#{target.created_at}")
	public Date getCreatedAt();
	
	@Value(value = "#{target.updated_at}")
	public Date getUpdatedAt();
	
	@Value(value = "#{target.first_name}")
	public String getFirstname();
	
	@Value(value = "#{target.last_name}")
	public String getLastname();
	
	@Value(value = "#{target.gender}")
	public String getGender();
	
	@Value(value = "#{target.email}")
	public String getEmail();
	
	@Value(value = "#{target.image}")
	public String getImage();
	
}
