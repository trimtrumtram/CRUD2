package com.crudapi.crud.service;

import com.crudapi.crud.dto.employee.CreateEmployeeDTO;
import com.crudapi.crud.enums.sort.EmployeeSortField;
import com.crudapi.crud.mapper.entityMapper.EmployeeMapper;
import com.crudapi.crud.specification.EmployeeSpecification;
import com.crudapi.crud.enums.sort.SortDirection;
import com.crudapi.crud.dto.employee.EmployeeFilterDTO;
import com.crudapi.crud.dto.employee.EmployeeResponseDTO;
import com.crudapi.crud.dto.employee.UpdateEmployeeDTO;
import com.crudapi.crud.model.Employee;
import com.crudapi.crud.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public EmployeeResponseDTO createEmployee(CreateEmployeeDTO dto) {
        log.info("Создание сотрудника: email={}", dto.getEmail());
        log.debug("Детали входного DTO: {}", dto);

        if (employeeRepository.existsEmployeeByEmail(dto.getEmail())) {
            log.error("Попытка создать сотрудника с уже существующим email: {}", dto.getEmail());
            throw new IllegalArgumentException("Email already exists");
        }

        Employee employee = employeeMapper.mapToEntity(dto);
        log.debug("Entity после маппинга: {}", employee);

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Сотрудник сохранён с id={}", savedEmployee.getId());

        return employeeMapper.mapToDTO(savedEmployee);
    }

    public EmployeeResponseDTO updateEmployee(Long id, UpdateEmployeeDTO dto) {
        log.info("Обновление сотрудника с id={}", id);
        log.debug("Детали UpdateEmployeeDTO: {}", dto);

        Employee employee = findEmployee(id, dto);
        log.debug("Найден сотрудник: {}", employee);

        validateEmail(employee, dto.getEmail());
        employeeMapper.updateEntityFromDTO(dto, employee);

        Employee updatedEmployee = employeeRepository.save(employee);
        log.info("Сотрудник обновлён: id={}", updatedEmployee.getId());

        return employeeMapper.mapToDTO(updatedEmployee);
    }

    public void deleteEmployee(Long id) {
        log.info("Удаление сотрудника с id={}", id);

        if (!employeeRepository.existsById(id)) {
            log.error("Попытка удалить несуществующего сотрудника: id={}", id);
            throw new IllegalArgumentException("Employee with id " + id + " not found");
        }

        employeeRepository.deleteById(id);
        log.info("Сотрудник с id={} успешно удалён", id);
    }

    public Page<EmployeeResponseDTO> getAllEmployees(EmployeeFilterDTO filter) {
        log.info("Получение списка сотрудников с фильтром: {}", filter);
        try {
            EmployeeSortField sortField = filter.getSortField() != null ? filter.getSortField() : EmployeeSortField.ID;
            Sort sort = Sort.by(sortField.getSortBy());
            sort = filter.getSortDirection() == SortDirection.DESC ? sort.descending() : sort.ascending();

            int page = filter.getPage() != null ? filter.getPage() : 0;
            int size = filter.getSize() != null ? filter.getSize() : 10;
            Pageable pageable = PageRequest.of(page, size, sort);

            log.debug("Параметры пагинации: page={}, size={}, sort={}", page, size, sort);

            Page<EmployeeResponseDTO> result = employeeRepository.findAll(
                    EmployeeSpecification.filterEmployee(
                            filter.getFirstName(),
                            filter.getLastName(),
                            filter.getRole() != null ? String.valueOf(filter.getRole()) : null,
                            filter.getEmail()
                    ),
                    pageable
            ).map(employeeMapper::mapToDTO);

            log.info("Найдено сотрудников: {}", result.getTotalElements());
            return result;
        } catch (Exception e) {
            log.error("Ошибка при получении сотрудников с фильтром {}: {}", filter, e.getMessage(), e);
            throw new IllegalArgumentException("Invalid filter: " + e.getMessage());
        }
    }

    private Employee findEmployee(Long id, UpdateEmployeeDTO dto) {
        log.debug("Поиск сотрудника по id={} или email={}", id, dto.getEmail());

        if (id != null) {
            return employeeRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Сотрудник с id={} не найден", id);
                        return new IllegalArgumentException("Employee with id " + id + " not found");
                    });
        }
        if (dto.getEmail() != null) {
            return employeeRepository.findEmployeeByEmail(dto.getEmail())
                    .orElseThrow(() -> {
                        log.error("Сотрудник с email={} не найден", dto.getEmail());
                        return new IllegalArgumentException("Employee with email " + dto.getEmail() + " not found");
                    });
        }

        log.error("Ошибка: email или id должны быть указаны для поиска сотрудника");
        throw new IllegalArgumentException("Email or id is required");
    }

    private void validateEmail(Employee employee, String newEmail) {
        if (newEmail != null && !newEmail.isBlank() && !newEmail.equals(employee.getEmail())) {
            log.info("Проверка уникальности email: {}", newEmail);

            if (employeeRepository.existsEmployeeByEmail(newEmail)) {
                log.error("Email {} уже существует", newEmail);
                throw new IllegalArgumentException("Employee with email " + newEmail + " already exists");
            }
        }
    }
}
