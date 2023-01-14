package com.ty.ems.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ty.ems.dao.EmployeeDao;
import com.ty.ems.dto.EmployeeRegisterDto;
import com.ty.ems.dto.EmployeeUpdateDto;
import com.ty.ems.dto.GetEmployeeDto;
import com.ty.ems.dto.JwtAuthRequestDTO;
import com.ty.ems.dto.JwtAuthResponce;
import com.ty.ems.email.EmailConfig;
import com.ty.ems.entity.EmployeePrimary;
import com.ty.ems.exception.EmployeeException;
import com.ty.ems.security.JwtUtil;

@Service
public class EmployeeServiceImplementation implements EmployeeService {

	ModelMapper modelMapper = new ModelMapper();

	@Autowired
	EmployeeDao dao;

	@Autowired
	EmailConfig config;

	@Autowired
	private JwtUtil jwtTokenUtil;
	@Autowired
	private AuthenticationManager authenticationManager;

	private int i;
	LocalDateTime myDateObj = LocalDateTime.now();
	DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MMyy");

	@Autowired
	PasswordEncoder encoder;

	@Override
	public JwtAuthResponce getLogin(JwtAuthRequestDTO loginDto) {

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getEmployeeId(), loginDto.getPassword()));

			EmployeePrimary employee = (EmployeePrimary) authentication.getPrincipal();
			String accessToken = jwtTokenUtil.generateAccessToken(employee);
			JwtAuthResponce jwtAuthResponce = new JwtAuthResponce(employee.getEmployeeId(), accessToken);
			return jwtAuthResponce;
		} catch (AuthenticationException e) {
			e.printStackTrace();
			throw new EmployeeException("unauthorize");
		}

	}

	@Override
	public EmployeeRegisterDto register(EmployeeRegisterDto dto) {

		try {
			EmployeePrimary empObj = new EmployeePrimary();
			 dto.setPassword(encoder.encode(dto.getPassword()));
			System.out.println(dto.getPassword());
             
			BeanUtils.copyProperties(dto, empObj);
			dao.save(empObj);
			i = empObj.getId();
			empObj.setEmployeeId("TYC" + myDateObj.format(myFormatObj) + String.format("%04d", i));
			dao.save(empObj);

			BeanUtils.copyProperties(empObj, dto);
			return dto;
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new EmployeeException("something went wrong");
		}

	}

	@Override
	public EmployeeUpdateDto update(EmployeeUpdateDto dto) {

		if (dao.findByEmployeeId(dto.getEmployeeId()) != null) {
			EmployeePrimary empObj = dao.findByEmployeeId(dto.getEmployeeId());
			System.out.println("@@@@@@@@@@@@@@@loop");

			BeanUtils.copyProperties(dto, empObj);
			dao.save(empObj);
//			GetEmployeeDto getEmployeeDto = new GetEmployeeDto();
			BeanUtils.copyProperties(empObj, dto);

			return dto;
		} else {
			throw new EmployeeException("Id Not Found");

		}
	}

	@Override
	public boolean delete(String empId) {

		if (dao.findByEmployeeId(empId) != null) {
			EmployeePrimary findByEmpId = dao.findByEmployeeId(empId);
			dao.delete(findByEmpId);
			return true;
		}
		throw new EmployeeException("Id Not Found");

	}

	@Override
	public GetEmployeeDto getEmployeeByEmpId(String empId) {

		if (dao.findByEmployeeId(empId) != null) {
			EmployeePrimary findByEmpId = dao.findByEmployeeId(empId);

			GetEmployeeDto getEmployeeDto = new GetEmployeeDto(findByEmpId.getEmployeeId(), findByEmpId.getEmpName(),
					findByEmpId.getEmpEmail(), findByEmpId.getPassword(), findByEmpId.getPhoneNumber(),
					findByEmpId.getBanks(), findByEmpId.getAddresses(), findByEmpId.getDepartments());

			return getEmployeeDto;
		}
		throw new EmployeeException("Id Not Found");
	}

	@Override
	public List<GetEmployeeDto> get() {

		// to store all data
		List<EmployeePrimary> findAll = dao.findAll();
		


		// for coping all data
		List<GetEmployeeDto> dtos = new ArrayList<>();

		for (EmployeePrimary findByEmpId : findAll) {
			GetEmployeeDto employeeRegisterDto = new GetEmployeeDto(findByEmpId.getEmployeeId(),
					findByEmpId.getEmpName(), findByEmpId.getEmpEmail(), findByEmpId.getPassword(),
					findByEmpId.getPhoneNumber(), findByEmpId.getBanks(), findByEmpId.getAddresses(),
					findByEmpId.getDepartments());
			dtos.add(employeeRegisterDto);
			

		}
		return dtos;

	}

	@Override
	public List<GetEmployeeDto> getEmployeeByName(String empName) {

		try {
			if (dao.findByEmpName(empName).isEmpty()) {
				throw new EmployeeException("user with " + empName + " Is Not Presente");
			}
			List<EmployeePrimary> findAll = dao.findByEmpName(empName);
			System.out.println(findAll);

			List<GetEmployeeDto> dtos = new ArrayList<>();

			for (EmployeePrimary findByEmpId : findAll) {
				GetEmployeeDto employeeRegisterDto = new GetEmployeeDto(findByEmpId.getEmployeeId(),
						findByEmpId.getEmpName(), findByEmpId.getEmpEmail(), findByEmpId.getPassword(),
						findByEmpId.getPhoneNumber(), findByEmpId.getBanks(), findByEmpId.getAddresses(),
						findByEmpId.getDepartments());
				dtos.add(employeeRegisterDto);
			}
			System.out.println("@@@@@@@@@@@@" + dtos);
			return dtos;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new EmployeeException("Something went wrong");

		}
	}
}
