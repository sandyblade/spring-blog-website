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

import com.api.backend.models.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT count(*) FROM public.users x WHERE x.id <> 0", nativeQuery = true)
	long count();
	
	@Query(value = "SELECT * FROM public.users x WHERE x.email = ?1 AND x.id <> ?2 LIMIT 1", nativeQuery = true)
	User findByEmail(String email, long id);
    
    @Query(value = "SELECT * FROM public.users x WHERE x.phone = ?1 AND x.id <> ?2 LIMIT 1", nativeQuery = true)
	User findByPhone(String phone, long id);
    
    @Query(value = "SELECT * FROM public.users x WHERE x.confirm_token = ?1 AND x.confirmed = 0 LIMIT 1", nativeQuery = true)
	User findByConfirmToken(String token);
    
    @Query(value = "SELECT * FROM public.users x WHERE x.reset_token = ?1 AND x.confirmed = 1 LIMIT 1", nativeQuery = true)
	User findByResetToken(String token);

}