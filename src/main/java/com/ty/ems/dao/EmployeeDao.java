package com.ty.ems.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.ems.entity.EmployeePrimary;

public interface EmployeeDao extends JpaRepository<EmployeePrimary, Integer>{
	
	 EmployeePrimary findByEmployeeId(String empId);
	
	// Optional<EmployeePrimary> findByEmployeeId(String empId);
	 

	 List<EmployeePrimary> findByEmpName(String empName);
	 	 
	 EmployeePrimary findByEmpNameAndEmployeeId(String name,String empId);

	
}
