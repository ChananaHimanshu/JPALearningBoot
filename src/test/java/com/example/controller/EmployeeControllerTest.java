package com.example.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.entity.Address;
import com.example.entity.Employee;
import com.example.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestInstance(Lifecycle.PER_CLASS)
public class EmployeeControllerTest {

	private EmployeeService mockEmployeeService;
	private MockMvc mockMvc;

	@BeforeAll
	public void setup() {
		this.mockEmployeeService = mock(EmployeeService.class);
		this.mockMvc = MockMvcBuilders.standaloneSetup(new EmployeeController(mockEmployeeService)).build();
	}

	public Employee getEmployee() {
		Employee emp = new Employee();
		emp.setId(new Long(1));
		emp.setName("Himanshu");
		Address address = new Address();
		address.setId(new Long(1));
		address.setStreet("Street1");
		address.setCity("Ambala");
		address.setState("New Delhi");
		emp.setAddress(address);
		return emp;
	}

	@Test
	public void saveEmployee_CallsServiceMethodAndReturnsStatus200() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Employee employee = getEmployee();
		String request = mapper.writeValueAsString(employee);
		System.out.println(request);
		this.mockMvc.perform(post("/employee").contentType(MediaType.APPLICATION_JSON).content(request))
				.andExpect(status().isOk());
		verify(mockEmployeeService).saveEmployee(employee);
	}

	@Test
	public void getEmployeeById_CallsServiceMethodAndReturnsStatus200() throws Exception {
		Employee employee = getEmployee();
		when(mockEmployeeService.getEmployee(new Long(1))).thenReturn(employee);
		String response = this.mockMvc.perform(get("/employee/1")).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();
		JSONAssert.assertEquals("{\"id\":1,\"name\":\"Himanshu\",\"address\":{\"id\":1,\"street\":\"Street1\",\"city\":\"Ambala\",\"state\":\"New Delhi\"}}", response, JSONCompareMode.STRICT);
		verify(mockEmployeeService).getEmployee(new Long(1));
	}

}
