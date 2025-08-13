package com.crudapi.crud.model;

import com.crudapi.crud.enums.entityEnums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column (name = "last_name", nullable = false)
    private String lastName;

    @Column (name = "email", nullable = false)
    private String email;

    @Column (name = "password", nullable = false)
    private String password;

    @Column (name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
}
