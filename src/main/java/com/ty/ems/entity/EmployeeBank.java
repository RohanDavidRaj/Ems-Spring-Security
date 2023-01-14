package com.ty.ems.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee_bank")
public class EmployeeBank {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int bankId;
	String accountType;
	String bankName;
	String ifscCode;
	

	

}
