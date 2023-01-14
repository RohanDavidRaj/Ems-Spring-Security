package com.ty.ems.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.juneau.json.JsonParser;
import org.apache.juneau.json.JsonSerializer;
import org.apache.juneau.parser.ParseException;
import org.apache.juneau.serializer.SerializeException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ty.ems.dao.EmployeeDao;
import com.ty.ems.dto.EmployeeRegisterDto;

@SpringBootTest
class EntityTest {

	


	@MockBean
	private EmployeeDao dao;

	private List<EmployeeBank> banks;
	private List<EmployeeAddress> addresses;

	private EmployeeDepartment department = new EmployeeDepartment(1, "name", "mag");




	//primary entity to json
	@Test
	public void EmployeeToJson() throws JsonMappingException, JsonProcessingException, SerializeException {
		EmployeePrimary primary = EmployeePrimary.builder().empEmail("rohan").empEmail("rohan@gmail.com")
				.employeeId("123").addresses(addresses).banks(banks).departments(department).password("11223334")
				.phoneNumber("7688376282").build();;

		JsonSerializer defaultReadable = JsonSerializer.DEFAULT_READABLE;
		String serialize = defaultReadable.serialize(primary);
		
		String json="{\r\n"
				+ "	\"departments\": {\r\n"
				+ "		\"deptId\": 1,\r\n"
				+ "		\"deptName\": \"name\",\r\n"
				+ "		\"manager\": \"mag\"\r\n"
				+ "	},\r\n"
				+ "	\"empEmail\": \"rohan@gmail.com\",\r\n"
				+ "	\"employeeId\": \"123\",\r\n"
				+ "	\"id\": 0,\r\n"
				+ "	\"password\": \"11223334\",\r\n"
				+ "	\"phoneNumber\": \"7688376282\"\r\n"
				+ "}";
		assertNotNull(serialize);
	}
	
	//primary entity to json
	@Test
	public void JsonToEmployee() throws JsonMappingException, JsonProcessingException, SerializeException, ParseException {
		EmployeePrimary primary = EmployeePrimary.builder().empEmail("rohan").empEmail("rohan@gmail.com")
				.employeeId("123").addresses(addresses).banks(banks).departments(department).password("11223334")
				.phoneNumber("7688376282").build();

		JsonParser jsonParser = JsonParser.DEFAULT;
		
		String jsonVal="{\r\n"
				+ "    \"empName\": \"rohan\",\r\n"
				+ "    \"empEmail\": \"rohan@gmail.com\",\r\n"
				+ "    \"password\": \"12237777\",\r\n"
				+ "    \"phoneNumber\": \"7588376183\",\r\n"
				+ "    \"banks\": [\r\n"
				+ "        {\r\n"
				+ "            \"accountType\": \"permanent\",\r\n"
				+ "            \"bankName\": \"kotak\",\r\n"
				+ "            \"ifscCode\": \"ifccode\"\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"addresses\": [\r\n"
				+ "        {\r\n"
				+ "            \"addressType\": \"encnec\",\r\n"
				+ "            \"address\": \"rrrr\",\r\n"
				+ "            \"pincode\": \"rrr\"\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"departments\": {\r\n"
				+ "        \"deptName\": \"name\",\r\n"
				+ "        \"manager\": \"mag\"\r\n"
				+ "    }\r\n"
				+ "}";
		EmployeeRegisterDto parse = jsonParser.parse(jsonVal, EmployeeRegisterDto.class);
		assertEquals(parse.getEmpName(),"rohan"); 
	}

}
