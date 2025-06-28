package com.crudapi.crud.mapper.filterMapper;

import com.crudapi.crud.enums.sort.SortDirection;
import com.crudapi.crud.enums.sort.EmployeeSortField;
import com.crudapi.crud.dto.employee.EmployeeFilterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeFilterMapper {
    @Mapping(source = "firstNameFilter", target = "firstName")
    @Mapping(source = "lastNameFilter", target = "lastName")
    @Mapping(source = "roleFilter", target = "role")
    @Mapping(source = "emailFilter", target = "email")
    @Mapping(source = "page", target = "page")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "sortBy", target = "sortField")
    @Mapping(source = "sortDirection", target = "sortDirection")
    EmployeeFilterDTO toDTO(
            String firstNameFilter,
            String lastNameFilter,
            String roleFilter,
            String emailFilter,
            Integer page,
            Integer size,
            EmployeeSortField sortBy,
            SortDirection sortDirection
    );
}
