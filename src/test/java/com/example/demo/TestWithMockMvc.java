package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

//@WebMvcTest - to test only web layer , that is the controller only, and mock service layer
//@DataJpaTest, @DataJdbcTest - to test only DAO layer

@SpringBootTest //loads complete application context - for integration test
@AutoConfigureMockMvc
class TestWithMockMvc {

	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	private EmployeeRepository repo;
	
	@Test
	void testGet() throws JsonProcessingException {
		try {
			mockMvc.perform(get("/test")).andExpect(status().isOk()).andExpect(jsonPath("$.message").value("OK"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testPost() throws JsonProcessingException {

		HttpHeaders headers = new HttpHeaders();
		headers.add("abc", "def");

		Employee anObject = new Employee();
		anObject.setName("Komal");
		anObject.setRole("Software Engineer");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(anObject);

		try {
			mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(requestJson))
					.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(header().string("Content-Type", "application/json"))
					.andExpect(jsonPath("$.name").value("Komal"))
					.andExpect(jsonPath("$.role").value("Software Engineer"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testGet_Not_Found() throws JsonProcessingException {
		try {
			mockMvc.perform(get("/employees/5")).andExpect(status().isNotFound())
					.andExpect(jsonPath("$.message").value("Could not find employee 5"))
					.andExpect(jsonPath("$.code").value("1001"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testGet_Found() throws JsonProcessingException {
		Employee emp = new Employee();
		emp.setName("Komal");
		emp.setRole("Engineer");
		
		Employee response = repo.save(emp);
		Assertions.assertEquals("Komal", response.getName());
		
		try {
			mockMvc.perform(get("/employees/"+response.getId())).andExpect(status().isOk())
					.andExpect(jsonPath("$.name").value("Komal"))
					.andExpect(jsonPath("$.id").value(response.getId()))
					.andExpect(jsonPath("$.role").value("Engineer"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
