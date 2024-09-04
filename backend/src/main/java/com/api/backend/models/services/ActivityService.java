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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.api.backend.models.entities.Activity;
import com.api.backend.models.entities.User;


public interface ActivityService {
	Page<Activity> findAll(Pageable pageable, long user_id, String keyword);
	Activity saveOrUpdate(Activity model);
	void saveActivity(User user, String Event, String Description);
}
