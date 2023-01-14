package com.ty.ems.dto;

import java.util.List;

import com.ty.ems.entity.EmployeeAddress;
import com.ty.ems.entity.EmployeeBank;
import com.ty.ems.entity.EmployeeDepartment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEmployeeDto {
	
		
	private String employeeId;

	private String empName;

	private String empEmail;
	
	private String password;
	
	private String phoneNumber;

	private List<EmployeeBank> banks;

	private List<EmployeeAddress> addresses;

	private EmployeeDepartment departments;

}
