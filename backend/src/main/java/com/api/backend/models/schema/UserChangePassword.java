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

package com.api.backend.models.schema;

public class UserChangePassword {

	private String OldPassword;
	private String NewPassword;
	private String PasswordConfirm;

	public String getOldPassword() {
		return OldPassword;
	}

	public void setOldPassword(String oldPassword) {
		OldPassword = oldPassword;
	}

	public String getNewPassword() {
		return NewPassword;
	}

	public void setNewPassword(String newPassword) {
		NewPassword = newPassword;
	}

	public String getPasswordConfirm() {
		return PasswordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		PasswordConfirm = passwordConfirm;
	}

}
