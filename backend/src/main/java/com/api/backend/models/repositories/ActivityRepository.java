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
import com.api.backend.models.entities.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long>  {
	
	@Query(value="SELECT * FROM public.activities WHERE user_id = ?1 AND (LOWER(x.event) LIKE %?2% OR LOWER(x.description) LIKE %?2%)", nativeQuery = true)
    Page<Activity> findAll(Pageable pageable, long user_id, String keyword);
	
}
