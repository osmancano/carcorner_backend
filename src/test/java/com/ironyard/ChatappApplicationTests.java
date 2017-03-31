package com.ironyard;

import com.ironyard.data.Car;
import com.ironyard.data.CarUser;
import com.ironyard.dto.TempCar;
import com.ironyard.repositories.CarRepo;
import com.ironyard.repositories.CarUserRepo;
import com.sun.javafx.tools.ant.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ChatappApplicationTests {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8"));

	private MockMvc mockMvc;

	private Long userid =  Long.parseLong("-1");

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	private CarUser account;

	private List<Car> carList = new ArrayList<>();

	@Autowired
	private CarRepo carRepo;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private CarUserRepo carUserRepo;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
				.findAny()
				.orElse(null);

		assertNotNull("the JSON message converter must not be null",
				this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();

		this.account = carUserRepo.findOne(userid);
		this.carList = carRepo.findByOwner(this.account);
	}

	@Test
	public void userNotFound() throws Exception {
		mockMvc.perform(post("open/users/-2/cars/")
				.content(this.json(new Car()))
				.contentType(contentType))
				.andExpect(status().isNotFound());
	}

	@Test
	public void readSingleCar() throws Exception {
		mockMvc.perform(get("/open/cars" + carList.get(0)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is(this.carList.get(0).getId().intValue())))
				.andExpect(jsonPath("$.make", is("Toyota")))
				.andExpect(jsonPath("$.description", is("A description")));
	}

	@Test
	public void readCars() throws Exception {
		mockMvc.perform(get("/open/users/" + userid + "/cars"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(this.carList.get(0).getId().intValue())))
				.andExpect(jsonPath("$[0].make", is("Toyota")))
				.andExpect(jsonPath("$[0].description", is("A description")))
				.andExpect(jsonPath("$[1].id", is(this.carList.get(1).getId().intValue())))
				.andExpect(jsonPath("$[1].make", is("Toytoa")))
				.andExpect(jsonPath("$[1].description", is("A description")));
	}

	@Test
	public void createCar() throws Exception {
		TempCar tempCar = new TempCar(userid,userid,12.5,userid);
		Car car = new Car(tempCar);
		try {
			String carJson = json(car);
			this.mockMvc.perform(post("/secure/cars/add")
					.contentType(contentType)
					.content(carJson))
					.andExpect(status().isCreated());
		}catch (Exception e){
			System.out.println(e.getMessage());
		}


	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(
				o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}
