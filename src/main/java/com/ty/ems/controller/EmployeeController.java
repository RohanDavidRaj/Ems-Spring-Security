package com.ty.ems.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ty.ems.dto.EmployeeRegisterDto;
import com.ty.ems.dto.EmployeeUpdateDto;
import com.ty.ems.dto.JwtAuthRequestDTO;
import com.ty.ems.dto.ResponseDto;
import com.ty.ems.service.EmployeeServiceImplementation;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeServiceImplementation service;
	
		
	@PostMapping("/login")
	public ResponseEntity<ResponseDto> getLogin(@RequestBody JwtAuthRequestDTO loginDto) {
		return new ResponseEntity<ResponseDto>(new ResponseDto(false, "login done", service.getLogin(loginDto)), HttpStatus.OK);

	}
	
	@PostMapping("/register")
	public ResponseEntity<ResponseDto> register(@Valid @RequestBody EmployeeRegisterDto dto) {

		
			return new ResponseEntity<ResponseDto>(new ResponseDto(false, "register successful", service.register(dto)),
					HttpStatus.OK);

	}

	@PutMapping("/update")
	public ResponseEntity<ResponseDto> update(@Valid @RequestBody EmployeeUpdateDto dto) {
			return new ResponseEntity<ResponseDto>(new ResponseDto(false, "update successful", service.update(dto)),
					HttpStatus.OK);
		

	} 

	@DeleteMapping("/delete/{empId}")
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponseDto> delete(@PathVariable String empId) {
		
		return new ResponseEntity<ResponseDto>(new ResponseDto(false, "delete Successfully",service.delete(empId)),HttpStatus.OK);

	}

	@GetMapping("/get/{empId}")
	public ResponseEntity<ResponseDto> getEmp(@PathVariable String empId) {

		return new ResponseEntity<ResponseDto>(new ResponseDto(false, " Data ", service.getEmployeeByEmpId(empId)),
				HttpStatus.OK);

	}

	@GetMapping("/getAll")
	public ResponseEntity<ResponseDto> getAll() {

		return new ResponseEntity<ResponseDto>(new ResponseDto(false, " Data ", service.get()), HttpStatus.OK);

	}
	
	@GetMapping("/name/{empName}")
	public ResponseEntity<ResponseDto> getByName(@PathVariable String empName){
          return new ResponseEntity<ResponseDto>(new ResponseDto(false, "Employees with name "+empName, service.getEmployeeByName(empName)), HttpStatus.OK)	;	
	}
	

}
