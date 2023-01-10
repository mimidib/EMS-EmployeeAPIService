package com.fdmgroup.RestAPI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.RestAPI.exception.EmployeeNotFoundException;
import com.fdmgroup.RestAPI.model.Employee;
import com.fdmgroup.RestAPI.repository.EmployeeRepository;

@Service
public class EmployeeService {

	EmployeeRepository repo;

	@Autowired
	public EmployeeService(EmployeeRepository repo) {
		super();
		this.repo = repo;
	}

	public Employee getEmployee(Long id) {
		return repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee with id = " + id + " not found."));
	}

	public Employee updateEmployee(Employee employee) {
		return repo.save(employee);
	}

	public void deleteEmployeeById(Long id) {
		repo.deleteById(id);	
	}

	public Employee createEmployee(Employee employee) {
		return repo.save(employee);
	}

	public List<Employee> getAllEmployees() {
		return repo.findAll();
	}

	public List<Employee> getEmployeeByFirstAndLastNames(String firstName, String lastName) {
		return repo.findByFirstNameAndLastName(firstName, lastName);
	}
	
	
}
