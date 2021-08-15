package com.example.demo;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

public class Service {
	@Autowired
    private Repository repository;

    public List<String> getStuffWithLengthLessThanFive() {
		try {
			return repository.getStuff().stream().filter(stuff -> stuff.length() < 5).collect(Collectors.toList());
		} catch (SQLException e) {
			return Arrays.asList();
		}
    }
    
    
    public void saveAStuff(String StuffName) {
    	repository.saveStuff(StuffName);
    }
}