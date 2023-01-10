package com.fdmgroup.RestAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fdmgroup.RestAPI.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	
	@Query("select e from Employee e where upper(e.firstName) like concat('%', upper(:firstName), '%') AND "
			+ "upper(e.lastName) like concat('%', upper(:lastName), '%')") // placeholder for @Param html attribute
	List<Employee> findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
