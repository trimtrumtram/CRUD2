package com.crudapi.crud.mapper.entityMapper;

import com.crudapi.crud.dto.employee.CreateEmployeeDTO;
import com.crudapi.crud.dto.employee.EmployeeResponseDTO;
import com.crudapi.crud.dto.employee.UpdateEmployeeDTO;
import com.crudapi.crud.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target = "id", ignore = true)
    Employee mapToEntity(CreateEmployeeDTO dto);
    @Mapping(target = "id", source = "id")
    EmployeeResponseDTO mapToDTO(Employee employee);

    void updateEntityFromDTO(UpdateEmployeeDTO dto, @MappingTarget Employee employee);
}
