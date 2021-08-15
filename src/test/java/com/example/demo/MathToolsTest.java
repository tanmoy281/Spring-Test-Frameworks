package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MathToolsTest {
	
	MathTools mathTools = new MathTools();

	@Test
    void testConvertToDecimalSuccess() {
        double result = mathTools.convertToDecimal(3, 4);
        Assertions.assertEquals(0.75, result);
        
        //Assertions.assertNotEquals(0.75, result);
    }
	
	@Test
	void testIsEvenSuccessful() {
		boolean result = mathTools.isEven(2);
		Assertions.assertTrue(result);
		
		result = mathTools.isEven(1);
		Assertions.assertFalse(result);
	}
	
	@Test
	void testConvertToDecimalInvalidDenominator() {
		
	    Assertions.assertThrows(IllegalArgumentException.class, () -> mathTools.convertToDecimal(3, 0));
	    
	    //Assertions.assertThrows(IllegalArgumentException.class, new Exe());
	}
	
	
	
	
	/*
	 * class Exe implements org.junit.jupiter.api.function.Executable{
	 * 
	 * @Override public void execute() throws Throwable {
	 * mathTools.convertToDecimal(3, 0);
	 * 
	 * } }
	 */
	 
}