package test;

import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import registration.controller.DemoController;
import registration.model.GeolocationData;
import registration.model.User;
import registration.service.GeolocationService;

class ControllerTest {
	MockMvc mockMvc;

	@Mock
	@Autowired
	private GeolocationService geolocationService;

	@InjectMocks
	private DemoController demoController;

	@BeforeEach
	public void init(){
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(demoController).build();
	}
	
	@Test
	void givenValidUser_whenAPIworks_thenReturnsAppropriateStatusAndMessage() throws Exception {

		User happyPathUser = new User();
		happyPathUser.setIpAddress("142.67.140.188");
		happyPathUser.setPassword("valid password");
		happyPathUser.setUsername("John Doe");

		GeolocationData happyPathUserGeolocation = new GeolocationData();
		happyPathUserGeolocation.setCity("Bedford");
		happyPathUserGeolocation.setCountry("Canada");


		String jsonString = "{\"username\": \"John Doe\",\"ipAddress\": \"142.67.140.188\",\"password\": \"valid password\"}";

		when(geolocationService.fetchGeo(happyPathUser)).thenReturn(happyPathUserGeolocation);

		this.mockMvc.perform(
			post("http://localhost:9596/registration")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.message").value("Welcome, John Doe from Bedford"));
				;
	}


	@Test
	void givenForeignUser_whenAPIworks_thenReturnsErrorStatusAndMessage() throws Exception {

		User abroadUser = new User();
		abroadUser.setIpAddress("104.97.20.0");
		abroadUser.setPassword("valid password");
		abroadUser.setUsername("John Doe");

		GeolocationData abroadUserGeolocation = new GeolocationData();
		abroadUserGeolocation.setCity("Cambridge");
		abroadUserGeolocation.setCountry("United States");


		String jsonString = "{\"username\": \"John Doe\",\"ipAddress\": \"104.97.20.0\",\"password\": \"valid password\"}";

		when(geolocationService.fetchGeo(abroadUser)).thenReturn(abroadUserGeolocation);

		this.mockMvc.perform(
			post("http://localhost:9596/registration")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				)
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string("This user is not eligible. Required to be in Canada."));
				;
	}

	@Test
	void givenIncompleteUser_whenAPIworks_thenReturnsErrorStatus() throws Exception {

		User emptyUsernameUser = new User();
		emptyUsernameUser.setIpAddress("142.67.140.188");
		emptyUsernameUser.setPassword("valid password");
		emptyUsernameUser.setUsername("");

		GeolocationData emptyUsernameUserGeolocation = new GeolocationData();
		emptyUsernameUserGeolocation.setCity("Bedford");
		emptyUsernameUserGeolocation.setCountry("Canada");


		String jsonString = "{\"username\": \"\",\"ipAddress\": \"142.67.140.188\",\"password\": \"valid password\"}";

		when(geolocationService.fetchGeo(emptyUsernameUser)).thenReturn(emptyUsernameUserGeolocation);

		this.mockMvc.perform(
			post("http://localhost:9596/registration")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				)
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				;
	}

	@Test
	void givenShortPassword_whenAPIworks_thenReturnsErrorStatusAndMessage() throws Exception {

		User shortPasswordUser = new User();
		shortPasswordUser.setIpAddress("142.67.140.188");
		shortPasswordUser.setPassword("short");
		shortPasswordUser.setUsername("John Doe");

		GeolocationData shortPasswordUserGeolocation = new GeolocationData();
		shortPasswordUserGeolocation.setCity("Bedford");
		shortPasswordUserGeolocation.setCountry("Canada");


		String jsonString = "{\"username\": \"\",\"ipAddress\": \"142.67.140.188\",\"password\": \"valid password\"}";

		when(geolocationService.fetchGeo(shortPasswordUser)).thenReturn(shortPasswordUserGeolocation);

		this.mockMvc.perform(
			post("http://localhost:9596/registration")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				)
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				// .andExpect(jsonPath("$.errors[0]").value("Password must have at least 8 characters"));
				;
	}


}
