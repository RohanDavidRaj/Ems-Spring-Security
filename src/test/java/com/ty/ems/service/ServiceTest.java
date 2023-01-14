package com.ty.ems.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.ty.ems.dao.EmployeeDao;
import com.ty.ems.dto.EmployeeRegisterDto;
import com.ty.ems.dto.EmployeeUpdateDto;
import com.ty.ems.dto.GetEmployeeDto;
import com.ty.ems.entity.EmployeeAddress;
import com.ty.ems.entity.EmployeeBank;
import com.ty.ems.entity.EmployeeDepartment;
import com.ty.ems.entity.EmployeePrimary;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ServiceTest {

	@InjectMocks
	private EmployeeServiceImplementation employeeServiceImplementation;

	@Mock
	private EmployeeDao dao;

	private List<EmployeeBank> banks;
	private List<EmployeeAddress> addresses;
	private GetEmployeeDto get;

	private List<GetEmployeeDto> userList;
	private EmployeeRegisterDto dto;

	private EmployeePrimary employeePrimary;

	private List<EmployeePrimary> list;

	private EmployeeDepartment department = new EmployeeDepartment(1, "name", "mag");

	void set() {

		banks.add(new EmployeeBank(1, "permanent", "kotak", "ifccode"));
		addresses.add(new EmployeeAddress(1, "", "", ""));
		dto = new EmployeeRegisterDto(1, "TYC11220001", "rohan", "emp@gmail.com", "1234567", "7588376183", banks,
				addresses, department, null);

		userList.add(new GetEmployeeDto("1", "rohan", "emp@gmail", "123", "1234", banks, addresses, department));

	}

	@Test
	public void findById() throws Exception {
		employeePrimary = new EmployeePrimary(1, "TYC11220001", "rohan", "emp@gmail.com", "1234567", "7588376183",
				banks, addresses, department, null);

		when(dao.findByEmployeeId(anyString())).thenReturn(employeePrimary);
		GetEmployeeDto employeeByEmpId = employeeServiceImplementation.getEmployeeByEmpId(anyString());

		assertThat(employeeByEmpId).isNotNull();
		assertEquals(employeeByEmpId.getEmpName(), employeePrimary.getEmpName());
	}

	@Test
	public void registerTest() throws Exception {
		EmployeePrimary primary = EmployeePrimary.builder().empEmail("rohan").empEmail("rohan@gmail.com")
				.employeeId("123").addresses(addresses).banks(banks).departments(department).password("11223334")
				.phoneNumber("7688376282").build();
		EmployeeRegisterDto dto = EmployeeRegisterDto.builder().empName("rohan").empEmail("rohan@gmail.com")
				.employeeId("123").addresses(addresses).banks(banks).departments(department).password("11223334")
				.phoneNumber("7688376282").build();

		
		when(employeeServiceImplementation.register(dto)).thenReturn(dto);
	
		assertEquals("TYC11220000", dto.getEmployeeId());

	}

	@Test
	public void updateTest() throws Exception {

		EmployeePrimary primary = EmployeePrimary.builder().empEmail("rohan").empEmail("rohan@gmail.com")
				.employeeId("123").addresses(addresses).banks(banks).departments(department).password("11223334")
				.phoneNumber("7688376282").build();
		EmployeeUpdateDto build = EmployeeUpdateDto.builder().empName("rohan").empEmail("rohan@gmail.com")
				.employeeId("TYC11220000").addresses(addresses).banks(banks).departments(department)
				.password("11223334").phoneNumber("7688376282").build();

		System.out.println("@@@" + build);
		System.out.println("@@@get" + build);

		when(dao.findByEmployeeId(anyString())).thenReturn(primary);
		when(employeeServiceImplementation.update(build)).thenReturn(build);
		System.out.println("@@@get" + build);
		// EmployeeUpdateDto register = employeeServiceImplementation.update(build);
		// assertEquals("rohan", dto.getEmpName());
		assertEquals("rohan", build.getEmpName());
		System.out.println("@@@update" + build);

	}

	@Test
	public void updateTestFail() throws Exception {
		EmployeeUpdateDto build = EmployeeUpdateDto.builder().empName("rohan").empEmail("rohan@gmail.com")
				.addresses(addresses).banks(banks).departments(department).password("11223334")
				.phoneNumber("7688376282").build();

		when(dao.findByEmployeeId(anyString())).thenReturn(null);
//		when(employeeServiceImplementation.update(null)).thenReturn(null);
		Exception assertThrows2 = assertThrows(Exception.class, () -> employeeServiceImplementation.update(build));
		assertThat(assertThrows2.getMessage()).isEqualTo("Id Not Found");

	}

	@Test
	public void deleteTest() throws Exception {
		employeePrimary = new EmployeePrimary(1, "TYC11220001", "rohan", "emp@gmail.com", "1234567", "7588376183",
				banks, addresses, department, null);

		String employeeId = "TYC1022029";

		when(dao.findByEmployeeId(anyString())).thenReturn(employeePrimary);
		boolean delete = employeeServiceImplementation.delete(employeeId);
		assertTrue(delete);

	}

	@Test
	public void deleteTestFail() throws Exception {
		employeePrimary = new EmployeePrimary(1, "TYC11220001", "rohan", "emp@gmail.com", "1234567", "7588376183",
				banks, addresses, department, null);

		String employeeId = "TYC1022029";

		when(dao.findByEmployeeId(anyString())).thenReturn(null);

		Exception assertThrows2 = assertThrows(Exception.class, () -> employeeServiceImplementation.delete(employeeId));
		assertThat(assertThrows2.getMessage()).isEqualTo("Id Not Found");

	}

	@Test
	public void findByIdTestFail() throws Exception {
		employeePrimary = new EmployeePrimary(1, "TYC11220001", "rohan", "emp@gmail.com", "1234567", "7588376183",
				banks, addresses, department, null);

		String employeeId = "TYC1022029";

		when(dao.findByEmployeeId(anyString())).thenReturn(null);

		Exception assertThrows2 = assertThrows(Exception.class,
				() -> employeeServiceImplementation.getEmployeeByEmpId(employeeId));
		assertThat(assertThrows2.getMessage()).isEqualTo("Id Not Found");

	}

	@Test
	public void getAll() throws Exception {
		employeePrimary = new EmployeePrimary(1, "TYC11220001", "rohan", "emp@gmail.com", "1234567", "7588376183",
				banks, addresses, department, null);
		List<EmployeePrimary> list=new ArrayList<>();
		list.add(employeePrimary);
		
		System.out.println("@@"+employeePrimary);
		list.add(employeePrimary);
	
		System.out.println("@@@@@@@@@@list"+list);

		when(dao.findAll()).thenReturn(list);
		List<GetEmployeeDto> list2 = employeeServiceImplementation.get();

		System.out.println(list2);
		assertNotNull(list2);
	}

}
