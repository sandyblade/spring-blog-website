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

import com.api.backend.models.dto.UserDetailDto;
import com.api.backend.models.entities.User;

public interface UserService {
	Long TotalRows();
	User saveOrUpdate(User model);
	User findByEmail(String email, long id);
	User findByPhone(String phone, long id);
	User findByConfirmToken(String token);
	User findByResetToken(String token);
	UserDetailDto detail(User user);
}
