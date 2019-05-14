package com.application.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.application.dao.EmployeeDao;
import com.application.model.Employee;
@Repository
public class EmployeeDaoImpl extends NamedParameterJdbcDaoSupport implements EmployeeDao {
	@Autowired
	private DataSource datasource;
	
	@Override
	public void createEmployee(Employee employee) {

		String SQL = "INSERT INTO employee_table(employee_name,email,gender,salary) VALUES(:empName,:email,:gender,:salary)";
		MapSqlParameterSource inputMap = new MapSqlParameterSource();
		inputMap.addValue("empName", employee.getEmployeeName());
		inputMap.addValue("email", employee.getEmail());
		inputMap.addValue("gender", employee.getGender());
		inputMap.addValue("salary", employee.getSalary());

		int update = getNamedParameterJdbcTemplate().update(SQL, inputMap);
		if (update > 0) {
			System.out.println("Employee is created");
		}

	}

	@Override
	public Employee getEmployeeById(int employeeId) {
		String SQL = "SELECT * FROM employee_table where employee_id=:employeeId";
		Map<String, Object> map = new HashMap<>();
		map.put("employeeId", employeeId);
		Employee employee = getNamedParameterJdbcTemplate().queryForObject(SQL, map, new EmployeeRowMapper());
		return employee;

	}

	@Override
	public void deleteEmployeeById(int employeeId) {
		String SQL = "DELETE FROM employee_table WHERE employee_id = :empId";
		MapSqlParameterSource inputMap = new MapSqlParameterSource();
		inputMap.addValue("empId", employeeId);
		int update = getNamedParameterJdbcTemplate().update(SQL, inputMap);
		if (update > 0) {
			System.out.println("the row is deleted");
		}

	}

	@Override
	public void updateEmployeeEmailById(String newEmail, int employeeId) {
		String SQL = "UPDATE employee_table set email=:email WHERE employee_Id=:emailId ";
		MapSqlParameterSource inputMap = new MapSqlParameterSource();
		inputMap.addValue("emailId", employeeId);
		inputMap.addValue("email", newEmail);
		int update = getNamedParameterJdbcTemplate().update(SQL, inputMap);
		if (update > 0) {
			System.out.println("the row is updated");
		}

	}

	@Override
	public List<Employee> getAllEmployeesDetails() {
		String SQL = "SELECT * FROM employee_table"; 
		return getNamedParameterJdbcTemplate().query(SQL, new EmployeeRowMapper()); 

	}
	
	@PostConstruct
	public void init(){
		setDataSource(datasource);
	}
}
