package com.ty.ems.dto;

import lombok.Data;

@Data
public class JwtAuthRequestDTO {
	
	private String employeeId;
	private  String password;

}
