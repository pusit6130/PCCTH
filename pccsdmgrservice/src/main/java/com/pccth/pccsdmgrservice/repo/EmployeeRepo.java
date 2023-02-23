package com.pccth.pccsdmgrservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pccth.pccsdmgrservice.model.Employee;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    void deleteEmployeeById(Long id);

    Optional<Employee> findEmployeeById(Long id);
    
    @Query(value = "SELECT * FROM employeemanager.employee where username = ?", nativeQuery = true)
    Employee loginEmployee(String username);
    
}

