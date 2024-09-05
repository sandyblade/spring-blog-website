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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.backend.models.dto.ActivityListDto;
import com.api.backend.models.entities.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long>  {
	
	@Query(value="SELECT x.id, x.event, x.description, x.created_at FROM public.activities x WHERE x.user_id = ?1 AND (LOWER(CAST(x.event as varchar)) LIKE %?2% OR LOWER(CAST(x.description as varchar)) LIKE %?2%)", nativeQuery = true)
    Page<ActivityListDto> findAll(Pageable pageable, long user_id, String keyword);
	
}
