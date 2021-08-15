package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MyControllerAdvice {

	@ResponseBody
	@ExceptionHandler(EmployeeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	ResponseMsg employeeNotFoundHandler(EmployeeNotFoundException ex) {
		ResponseMsg msg = new ResponseMsg();
		msg.setCode("1001");
		msg.setMessage(ex.getMessage());
		return msg;
	}
}
