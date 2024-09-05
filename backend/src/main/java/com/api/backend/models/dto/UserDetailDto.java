package com.api.backend.models.dto;

import java.util.Date;

public class UserDetailDto {

	private Long Id;
	private String Email;
	private String Phone;
	private String Image;
	private String FullName;
	private String Gender;
	private String Country;
	private String Facebook;
	private String Instagram;
	private String Twitter;
	private String LinkedIn;
	private String Address;
	private String AboutMe;
	private Date CreatedAt;
	private Date UpdatedAt;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

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

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public String getFullName() {
		return FullName;
	}

	public void setFullName(String fullName) {
		FullName = fullName;
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

	public Date getCreatedAt() {
		return CreatedAt;
	}

	public void setCreatedAt(Date createdAt) {
		CreatedAt = createdAt;
	}

	public Date getUpdatedAt() {
		return UpdatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		UpdatedAt = updatedAt;
	}

}
