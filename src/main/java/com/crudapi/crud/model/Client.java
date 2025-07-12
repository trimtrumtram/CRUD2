package com.crudapi.crud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "client" )
public class Client {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column (name = "last_name", nullable = false)
    private String lastName;

    @Column (name = "email", nullable = false)
    private String email;

    @Column (name = "phone", nullable = false)
    private String phone;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
}
