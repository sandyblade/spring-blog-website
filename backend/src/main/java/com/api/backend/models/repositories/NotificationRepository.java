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

package com.api.backend.models.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.api.backend.models.dto.NotificationListDto;
import com.api.backend.models.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	
	@Query(value="SELECT x.id, x.subject, x.message, x.created_at FROM public.notifications x WHERE x.user_id = ?1 AND (LOWER(CAST(x.subject as varchar)) LIKE %?2% OR LOWER(CAST(x.message as varchar)) LIKE %?2%)", nativeQuery = true)
    Page<NotificationListDto> findAll(Pageable pageable, long user_id, String keyword);
	
	@Query(value = "SELECT * from public.notifications x WHERE x.id = ?1 AND x.user_id = ?2 LIMIT 1", nativeQuery = true)
	Notification findById(long id, long user_id);

}
