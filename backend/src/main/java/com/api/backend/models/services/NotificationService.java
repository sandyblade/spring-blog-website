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
import com.api.backend.models.entities.Notification;

public interface NotificationService {
	Page<Notification> findAll(Pageable pageable, long user_id, String keyword);
	Notification findById(long id, long user_id);
	Notification saveOrUpdate(Notification model);
	void Remove(long id, long user_id);
}
