package com.ty.ems.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.ty.ems.dto.EmployeeRegisterDto;
import com.ty.ems.dto.EmployeeUpdateDto;
import com.ty.ems.dto.GetEmployeeDto;
import com.ty.ems.dto.ResponseDto;
import com.ty.ems.entity.EmployeeAddress;
import com.ty.ems.entity.EmployeeBank;
import com.ty.ems.entity.EmployeeDepartment;
import com.ty.ems.service.EmployeeServiceImplementation;

@WebMvcTest
@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = EmployeeController.class)
class EmployeeControllerTest {

//	@Autowired()
//	private MockMvc mockMvc;

	@MockBean
	private EmployeeServiceImplementation employeeServiceImplementation;

	@InjectMocks
	private EmployeeController controller;
//	@Autowired
//	private ObjectMapper mapper;

//	@Autowired
//	private WebApplicationContext context;

	private List<EmployeeBank> banks;
	private List<EmployeeAddress> addresses;
	private GetEmployeeDto get;
	private List<GetEmployeeDto> userList;
	private EmployeeRegisterDto dto;
	private EmployeeUpdateDto updateDto;

	private EmployeeDepartment department = new EmployeeDepartment(1, "name", "mag");

	
	void set() {

		banks.add(new EmployeeBank(1, "permanent", "kotak", "ifccode"));
		addresses.add(new EmployeeAddress(1, "", "", ""));
		dto = new EmployeeRegisterDto(1, "TYC11220001", "rohan", "emp@gmail.com", "1234567", "7588376183", banks,
				addresses, department,null);
		updateDto = new EmployeeUpdateDto("TYC11220001", "rohan", "emp@gmail.com", "1234567", "7588376183", banks,
				addresses, department);

		userList.add(new GetEmployeeDto("1", "rohan", "emp@gmail", "123", "1234", banks, addresses, department));
	}

	@BeforeEach
	public void setUp() {
		// mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		MockitoAnnotations.initMocks(this);

	}

	// Test Cases For Controller

	// Test Cases For Register
	@Test
	public void testRegsiter() throws Exception {
		when(employeeServiceImplementation.register(dto)).thenReturn(dto);
		ResponseEntity<ResponseDto> emp = controller.register(dto);
		assertNotNull(emp);
		assertEquals(HttpStatus.OK, emp.getStatusCode());

	}

	@Test
	public void testUpdate() throws Exception{
		when(employeeServiceImplementation.update(updateDto)).thenReturn(updateDto);
		ResponseEntity<ResponseDto> update = controller.update(updateDto);
		assertNotNull(update);
		assertEquals(HttpStatus.OK,update.getStatusCode());
	}

	@Test
	void testGetById() throws Exception {

		get = new GetEmployeeDto("1", "rohan", "emp@gmail.com", "12345678", "7588376183", banks, addresses, department);
		when(employeeServiceImplementation.getEmployeeByEmpId(anyString())).thenReturn(get);
		ResponseEntity<ResponseDto> emp = controller.getEmp(get.getEmployeeId());
		assertNotNull(emp);
		assertEquals(HttpStatus.OK, emp.getStatusCode());

	}

	@Test
	void testGetAll() throws Exception {

		when(employeeServiceImplementation.get()).thenReturn(userList);
		ResponseEntity<ResponseDto> emp = controller.getAll();
		assertNotNull(emp);
		assertEquals(HttpStatus.OK, emp.getStatusCode());
	}

	@Test
	void testDelete() throws Exception {

		get = new GetEmployeeDto("1", "rohan", "emp@gmail.com", "12345678", "7588376183", banks, addresses, department);
		when(employeeServiceImplementation.delete(anyString())).thenReturn(true);
		ResponseEntity<ResponseDto> emp = controller.delete(anyString());
		assertEquals(HttpStatus.OK, emp.getStatusCode());
	}
}

