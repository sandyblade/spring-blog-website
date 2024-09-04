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

package com.api.backend.data;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.github.javafaker.Faker;
import com.api.backend.config.EncoderConfig;
import com.api.backend.helpers.CommonHelper;
import com.api.backend.models.entities.User;
import com.api.backend.models.services.UserService;

@Component
public class Seeder {

	private static int MAX_USER = 10;
	private static String DEFAULT_PASSWORD = "P@ssw0rd!123";

	@Autowired
	private UserService UserService;

	@EventListener
	public void run(ContextRefreshedEvent event) throws Exception {
		this.CreateUser();
	}

	private void CreateUser() throws ParseException {
		long TotalRows = UserService.TotalRows();
		if (TotalRows == 0) {
			for (int i = 1; i <= MAX_USER; i++) {
				Faker faker = new Faker();
				EncoderConfig enc = new EncoderConfig();
				int max = 2;
				int min = 1;
				int genderIndex = min + (int) (Math.random() * ((max - min) + 1));
				String Gender = genderIndex == 1 ? "M" : "F";
				User user = new User();
				user.setEmail(faker.internet().emailAddress());
				user.setPhone(faker.phoneNumber().cellPhone());
				user.setPassword(enc.passwordEncoder().encode(DEFAULT_PASSWORD));
				user.setFirstName(faker.name().firstName());
				user.setLastName(faker.name().lastName());
				user.setGender(Gender);
				user.setCountry(faker.address().country());
				user.setFacebook(CommonHelper.username(faker.name().firstName()+" "+faker.name().lastName()));
				user.setTwitter(CommonHelper.username(faker.name().firstName()+" "+faker.name().lastName()));
				user.setInstagram(CommonHelper.username(faker.name().firstName()+" "+faker.name().lastName()));
				user.setLinkedIn(CommonHelper.username(faker.name().firstName()+" "+faker.name().lastName()));
				user.setAddress(faker.address().fullAddress());
				user.setAboutMe(faker.lorem().paragraph());
				user.setCreatedAt(CommonHelper.DateNow());
				user.setUpdatedAt(CommonHelper.DateNow());
				user.setConfirmed(1);
				UserService.saveOrUpdate(user);
			}
		}
	}

}
