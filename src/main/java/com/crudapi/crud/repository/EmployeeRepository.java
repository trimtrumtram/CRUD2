package com.crudapi.crud.repository;

import com.crudapi.crud.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    boolean existsEmployeeByEmail(String Email);

    Optional<Employee> findEmployeeByEmail(String Email);
}
