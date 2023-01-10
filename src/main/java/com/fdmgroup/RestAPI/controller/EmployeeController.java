package com.fdmgroup.RestAPI.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fdmgroup.RestAPI.model.Employee;
import com.fdmgroup.RestAPI.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

	EmployeeService service;
	
	@Autowired
	public EmployeeController(EmployeeService service) {
		super();
		this.service = service;
	}
	
	@Operation(summary = "Save an Employee to Database")
	@ApiResponse(description = "Saves an Employee entry from input to the Database")
	@PostMapping("/add-employee")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
		Employee managedEmployee = service.createEmployee(employee);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
						.buildAndExpand(managedEmployee.getId()).toUri();
		
		return ResponseEntity.created(location).body(managedEmployee);
	}
	
	@Operation(summary = "Retrieve all Employees from Database")
	@GetMapping
	public List<Employee> getAllEmployees(){
		return service.getAllEmployees();
	}
	
	/**
	 * @param id
	 * @return Employee if provided Id is found, otherwise throw EmployeeNotFoundException. 
	 * @see com.fdmgroup.RestAPI.exception.EmployeeNotFoundException
	 */
	@Operation(summary = "Get an Employee by ID from Database")
	@ApiResponses(value = {
			@ApiResponse(description = "Returns the Employee with ID input", responseCode = "200" , content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)	}),
			@ApiResponse(description = "When no Employee exist with input ID ", responseCode = "404", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)	})
	})
	@GetMapping("/{id}")
	public Employee getEmployee(@PathVariable Long id) {
		return service.getEmployee(id); 
	}
	
	@Operation(summary = "Find an Employee by first and last name")
	@ApiResponses(value = {
			@ApiResponse(description = "Returns the Employee with firstName and lastName input", responseCode = "200" , content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)	}),
			@ApiResponse(description = "When no Employee exist with input firstName and lastName ", responseCode = "404", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)	})
	})
	@GetMapping("/{firstName}/{lastName}")
	public List<Employee> getEmployeeByFirstAndLastNames(@PathVariable String firstName, @PathVariable String lastName) {
		return service.getEmployeeByFirstAndLastNames(firstName, lastName);
	}
	
	/**
	 * @param employee
	 * @return A ResponseEntity containing the CREATED status and location header of given URI.
	 */
	@Operation(summary = "Update an Employee's details")
	@PutMapping
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
		Employee returnedEmployee = service.updateEmployee(employee);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(returnedEmployee.getId()).toUri();
		
		return ResponseEntity.created(location).body(returnedEmployee);
		
	}
	
	/**
	 * @param id
	 * @return A ResponseEntity with the status OK.
	 */
	@Operation(summary = "Delete an Employee with ID input")
	@DeleteMapping("/{id}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable Long id){
		service.deleteEmployeeById(id);
		return ResponseEntity.ok().build();
	}
	
}
