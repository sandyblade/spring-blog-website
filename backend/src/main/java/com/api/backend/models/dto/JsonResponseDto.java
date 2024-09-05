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

package com.api.backend.models.dto;

public class JsonResponseDto {

	private boolean Status;
	private String Message;
	private Object Data;

	public JsonResponseDto(boolean _status, String _message, Object _data) {
		this.Status = _status;
		this.Message = _message;
		this.Data = _data;
	}

	public boolean isStatus() {
		return this.Status;
	}

	public boolean getStatus() {
		return this.Status;
	}

	public void setStatus(boolean Status) {
		this.Status = Status;
	}

	public String getMessage() {
		return this.Message;
	}

	public void setMessage(String Message) {
		this.Message = Message;
	}

	public Object getData() {
		return this.Data;
	}

	public void setData(Object Data) {
		this.Data = Data;
	}

}
