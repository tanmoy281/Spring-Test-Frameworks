package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ServiceTest {

	@Mock
	Repository repository;

	@InjectMocks
	Service service;//class under test

	@Test
	void testSuccess() throws SQLException {
		// Setup mock scenario
		Mockito.when(repository.getStuff()).thenReturn(Arrays.asList("A", "B", "CDEFGHIJK", "12345", "1234"));

		// Execute the service that uses the mocked repository
		List<String> stuff = service.getStuffWithLengthLessThanFive();

		// Validate the response
		Assertions.assertNotNull(stuff);
		Assertions.assertEquals(3, stuff.size());
		
		Assertions.assertEquals("A", stuff.get(0));
		Assertions.assertEquals("B", stuff.get(1));
		Assertions.assertEquals("1234", stuff.get(2));
	}

	@Test
	void testException() throws SQLException {
		// Setup mock scenario
		Mockito.when(repository.getStuff()).thenThrow(new SQLException("Connection Exception"));

		// Execute the service that uses the mocked repository
		List<String> stuff = service.getStuffWithLengthLessThanFive();

		// Validate the response
		Assertions.assertNotNull(stuff);
		Assertions.assertEquals(0, stuff.size());
	}

	@Test
	void testSave() {
		
		doNothing().when(repository).saveStuff("TestStuff");
		service.saveAStuff("TestStuff");
		verify(repository, times(1)).saveStuff("TestStuff");

	}

}