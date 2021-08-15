package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeRepository repository;

	@GetMapping("/test")
	public ResponseMsg getAllUsers() {
		ResponseMsg msg = new ResponseMsg();
		msg.setCode("1000");
		msg.setMessage("OK");
		return msg;
	}

	@GetMapping("/employees")
	public List<Employee> all() {
		return repository.findAll();
	}

	@PostMapping("/employees")
	public Employee newEmployee(@RequestBody Employee newEmployee) {
		return repository.save(newEmployee);
	}

	@GetMapping("/employees/{id}")
	public Employee one(@PathVariable Long id) {

		return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
	}

	@DeleteMapping("/employees/{id}")
	public void deleteEmployee(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@PutMapping("/employees/{id}")
	public Employee replaceEmployee(@RequestBody EmployeeDto newEmployee, @PathVariable Long id) {

		Employee emp = new Employee();
		return repository.findById(id).map(employee -> {
			employee.setName(newEmployee.getName());
			employee.setRole(newEmployee.getRole());
			return repository.save(employee);
		}).orElseGet(() -> {
			emp.setId(id);
			emp.setName(newEmployee.getName());
			emp.setRole(newEmployee.getRole());
			return repository.save(emp);
		});
	}
}