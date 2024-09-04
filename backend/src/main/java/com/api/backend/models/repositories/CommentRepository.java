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

import com.api.backend.models.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
