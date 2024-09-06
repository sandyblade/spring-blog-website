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
import com.api.backend.models.dto.NotificationListDto;
import com.api.backend.models.entities.Notification;
import com.api.backend.models.entities.User;

public interface NotificationService {
	List<NotificationListDto> findAll(long user_id, int start, int length, String orderby, String orderdir, String search);
	Notification findById(long id, long user_id);
	Notification saveOrUpdate(Notification model);
	void Remove(long id, long user_id);
	void sendNotif(User user, String Subject, String Message);
}
