package com.example.demo;

class MathTools {
	
	public double convertToDecimal(int numerator, int denominator) {
		if (denominator == 0) {
			throw new IllegalArgumentException("Denominator must not be 0");
		}
		return (double) numerator / (double) denominator;
	}

	public boolean isEven(int number) {
		return number % 2 == 0;
	}

}