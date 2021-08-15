package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class Integrationtest {

	@Autowired
	private EmployeeController controller;
	
	//use TestRestTemplate
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	@Test
	public void testTheApp() throws Exception {
		assertThat(restTemplate.getForObject("http://localhost:" + port + "/test",
				String.class)).contains("OK");
	}
	
	@Test
	public void testInsertNewEmployee() throws Exception {
		
		String url = "http://localhost:" + port + "/employees";
		URI uri = new URI(url);
		
		Employee emp = new Employee();
		emp.setName("Komal");
		emp.setRole("Software Engineer");
		
		HttpHeaders headers = new HttpHeaders();
        
		HttpEntity<Employee> request = new HttpEntity<>(emp, headers);
		
		ResponseEntity<Employee> response = restTemplate.postForEntity(uri,request,
				Employee.class);
		
		Assertions.assertEquals(200, response.getStatusCodeValue());
		Assertions.assertEquals("Komal", response.getBody().getName());
		Assertions.assertEquals("Software Engineer", response.getBody().getRole());
	}
	
	//or use mockMvc
	
	//use capture argument
	
}
