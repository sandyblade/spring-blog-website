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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.backend.models.dto.ActivityListDto;
import com.api.backend.models.entities.Activity;
import com.api.backend.models.entities.User;


public interface ActivityService {
	List<ActivityListDto> findAll(long user_id, int start, int length, String orderby, String orderdir, String search);
	Activity saveOrUpdate(Activity model);
	void saveActivity(User user, String Event, String Description);
}
