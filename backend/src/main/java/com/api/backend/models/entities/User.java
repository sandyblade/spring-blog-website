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

package com.api.backend.models.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import java.util.Date;

@Entity
@Table(name = "users", indexes = { @Index(columnList = "Email"), @Index(columnList = "Password"),
		@Index(columnList = "Phone"), @Index(columnList = "Image"), @Index(columnList = "FirstName"),
		@Index(columnList = "LastName"), @Index(columnList = "Gender"), @Index(columnList = "Country"),
		@Index(columnList = "Facebook"), @Index(columnList = "Instagram"), @Index(columnList = "Twitter"),
		@Index(columnList = "LinkedIn"), @Index(columnList = "ConfirmToken"), @Index(columnList = "ResetToken"),
		@Index(columnList = "Confirmed"), @Index(columnList = "CreatedAt"), @Index(columnList = "UpdatedAt") })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Column(nullable = false, columnDefinition = "varchar(180)", unique = true)
	private String Email;

	@Column(nullable = false, columnDefinition = "varchar(255)")
	private String Password;

	@Column(nullable = true, columnDefinition = "varchar(64)", unique = true)
	private String Phone;

	@Column(nullable = true, columnDefinition = "varchar(255)")
	private String Image;

	@Column(nullable = true, columnDefinition = "varchar(191)")
	private String FirstName;

	@Column(nullable = true, columnDefinition = "varchar(191)")
	private String LastName;

	@Column(nullable = true, columnDefinition = "varchar(2)")
	private String Gender;

	@Column(nullable = true, columnDefinition = "varchar(191)")
	private String Country;

	@Column(nullable = true, columnDefinition = "varchar(191)")
	private String Facebook;

	@Column(nullable = true, columnDefinition = "varchar(191)")
	private String Instagram;

	@Column(nullable = true, columnDefinition = "varchar(191)")
	private String Twitter;

	@Column(nullable = true, columnDefinition = "varchar(191)")
	private String LinkedIn;

	@Column(nullable = true, columnDefinition = "text")
	private String Address;

	@Column(nullable = true, columnDefinition = "text")
	private String AboutMe;

	@Column(nullable = true, columnDefinition = "varchar(36)")
	private String ConfirmToken;

	@Column(nullable = true, columnDefinition = "varchar(36)")
	private String ResetToken;

	@ColumnDefault("0")
	@Column(columnDefinition = "int2")
	private int Confirmed;

	@Column(nullable = true)
	private Date CreatedAt;

	@Column(nullable = true)
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

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
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

	public String getConfirmToken() {
		return ConfirmToken;
	}

	public void setConfirmToken(String confirmToken) {
		ConfirmToken = confirmToken;
	}

	public String getResetToken() {
		return ResetToken;
	}

	public void setResetToken(String resetToken) {
		ResetToken = resetToken;
	}

	public int getConfirmed() {
		return Confirmed;
	}

	public void setConfirmed(int confirmed) {
		Confirmed = confirmed;
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
