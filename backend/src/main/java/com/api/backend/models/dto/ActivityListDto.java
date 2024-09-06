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

public interface ActivityListDto {
	
	public Long getId();
	
	@Value(value = "#{target.event}")
	public String getEvent();
	
	@Value(value = "#{target.description}")
	public String getDescription();

	@Value(value = "#{target.created_at}")
	public Date getCreatedAt();

}
