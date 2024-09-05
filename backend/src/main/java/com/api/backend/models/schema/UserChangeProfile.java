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

public class UserChangeProfile {

	private String Email;
	private String Phone;
	private String FirstName;
	private String LastName;
	private String Gender;
	private String Country;
	private String Facebook;
	private String Instagram;
	private String Twitter;
	private String LinkedIn;
	private String Address;
	private String AboutMe;

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getFacebook() {
		return Facebook;
	}

	public void setFacebook(String facebook) {
		Facebook = facebook;
	}

	public String getInstagram() {
		return Instagram;
	}

	public void setInstagram(String instagram) {
		Instagram = instagram;
	}

	public String getTwitter() {
		return Twitter;
	}

	public void setTwitter(String twitter) {
		Twitter = twitter;
	}

	public String getLinkedIn() {
		return LinkedIn;
	}

	public void setLinkedIn(String linkedIn) {
		LinkedIn = linkedIn;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getAboutMe() {
		return AboutMe;
	}

	public void setAboutMe(String aboutMe) {
		AboutMe = aboutMe;
	}

}
