package com.ty.ems.service;

import java.util.List;

import com.ty.ems.dto.EmployeeRegisterDto;
import com.ty.ems.dto.EmployeeUpdateDto;
import com.ty.ems.dto.GetEmployeeDto;
import com.ty.ems.dto.JwtAuthRequestDTO;
import com.ty.ems.dto.JwtAuthResponce;

public interface EmployeeService {

	public JwtAuthResponce getLogin(JwtAuthRequestDTO loginDto);
	
	public EmployeeRegisterDto register(EmployeeRegisterDto dto);

	public EmployeeUpdateDto update(EmployeeUpdateDto dto);

	public boolean delete(String empId);

//
//	public GetEmployeeDto getEmployee(String empId);
//	
	public GetEmployeeDto getEmployeeByEmpId(String empId);

//
	public List<GetEmployeeDto> get();

//    
	public List<GetEmployeeDto> getEmployeeByName(String empName);

}
