package com.example.it210finalproject.model.entity;

import com.example.it210finalproject.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;
    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "phone", nullable = false, length = 10, unique = true)
    private String phone;
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "balance", nullable = false, columnDefinition = "DECIMAL(10, 2) CHECK (balance >= 0)")
    private Double balance;
}
