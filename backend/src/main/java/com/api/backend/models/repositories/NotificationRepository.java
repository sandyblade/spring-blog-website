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
import com.api.backend.models.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	
	@Query(value="SELECT * FROM public.notifications WHERE user_id = ?1 AND (LOWER(x.subject) LIKE %?2% OR LOWER(x.message) LIKE %?2% )", nativeQuery = true)
    Page<Notification> findAll(Pageable pageable, long user_id, String keyword);
	
	@Query(value = "SELECT * from public.notifications x WHERE x.id = ?1 AND x.user_id = ?2 LIMIT 1", nativeQuery = true)
	Notification findById(long id, long user_id);

}
