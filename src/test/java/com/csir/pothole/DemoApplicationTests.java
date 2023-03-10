package com.csir.pothole;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.csir.pothole.models.Subscriber;
import com.csir.pothole.repository.SubscriberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration 
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	SubscriberRepository subscriberRepo;

	@Test
	void contextLoads() throws JsonProcessingException, Exception {
		Subscriber subscriber = new Subscriber();
		subscriber.setName("Bryan");
		subscriber.setTelephoneNumber("0545730281");

		mockMvc.perform(
				post("/register").contentType("application/json").content(objectMapper.writeValueAsString(subscriber)))
				.andExpect(status().isOk());

		Subscriber sub = subscriberRepo.findByTelephoneNumber("0545730281").get();
		assertThat(sub.getName()).isEqualTo(subscriber.getName());
	}

}
