package com.example.demo;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Repository {
    public List<String> getStuff() throws SQLException{
        // Execute Query

        // Return results
        return Arrays.asList("One", "Two", "Three");
    }
    
    public void saveStuff(String StuffName) {
    	//do saving into db.
    }
}