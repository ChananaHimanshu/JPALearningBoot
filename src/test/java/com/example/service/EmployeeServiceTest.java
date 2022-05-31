package com.example.service;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.entity.Address;
import com.example.entity.Employee;
import com.example.exception.NotFoundException;
import com.example.repo.EmployeeRepository;
import com.example.repo.EmployeeRepositoryNew;

@TestInstance(Lifecycle.PER_CLASS)
public class EmployeeServiceTest {

	@Mock
	private EmployeeRepository mockEmployeeRepository;

	@Mock
	private EmployeeRepository mockEmployeeRepositoryAgain;

	private EmployeeRepositoryNew mockEmployeeRepositoryNew;

	private EmployeeService employeeService;

	@BeforeAll
	public void setup() {
		MockitoAnnotations.initMocks(this);
		// this.mockEmployeeRepository = mock(EmployeeRepository.class);
		this.employeeService = new EmployeeService(mockEmployeeRepository, mockEmployeeRepositoryNew);
		System.out.println("------------------" + this.employeeService);
		System.out.println(this.mockEmployeeRepository);
		System.out.println(this.mockEmployeeRepositoryAgain);
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
	public void getEmployee_mustSaveEmployee() throws NotFoundException {
		Employee emp = getEmployee();
		when(this.mockEmployeeRepository.findById(emp.getId())).thenReturn(Optional.of(emp));
		doReturn(Optional.of(emp)).when(this.mockEmployeeRepository).findById(emp.getId());
		Employee empdb = this.employeeService.getEmployee(emp.getId());
		Assertions.assertEquals(emp.getName(), empdb.getName());
		Assertions.assertEquals(emp.getAddress(), empdb.getAddress());
		Assertions.assertEquals(emp, empdb);
	}

	@Test
	public void findEmployee_withInvalidId_mustReturnException() {
		Employee emp = getEmployee();
//		Exception thrown = Assertions.assertThrows(NotFoundException.class,
//				() -> this.employeeService.getEmployee(emp.getId()), "Employee not found");
//		Assertions.assertTrue(thrown.getMessage().equals("Employee not found"));
//		verify(this.mockEmployeeRepository).findById(emp.getId());
	}

	@Test
	public void findEmployee_withName_mustReturnEmployee() {
		Employee emp = getEmployee();
		when(this.mockEmployeeRepository.getEmployeeByAddress(emp.getName())).thenReturn(Arrays.asList(emp));
		List<Employee> empdb = this.employeeService.getEmployeeByName(emp.getName());
		System.out.println(empdb);
		Assertions.assertEquals(emp, empdb.get(0));
	}

	@Test
	public void getEmployees_mustReturnEmployees() {
		Employee emp = getEmployee();
		when(this.mockEmployeeRepository.findAll()).thenReturn(Arrays.asList(emp));
		List<Employee> empdb = this.employeeService.getEmployee();
		Assertions.assertEquals(emp, empdb.get(0));
	}

}
