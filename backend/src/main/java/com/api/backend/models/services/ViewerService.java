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

import com.api.backend.models.entities.Viewer;

public interface ViewerService {
	long count(long user_id, long article_id);
	long countByArticle(long article_id);
	Viewer saveOrUpdate(Viewer model);
}
