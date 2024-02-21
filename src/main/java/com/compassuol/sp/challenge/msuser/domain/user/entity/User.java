package com.compassuol.sp.challenge.msuser.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false, length = 120)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 120)
    private String lastName;
    @Column(name = "cpf", nullable = false, unique = true, length = 14)
    private String cpf;
    @Column(name = "birthdate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Column(name = "email", nullable = false, unique = true, length = 120)
    private String email;
    @Column(name = "cep", nullable = false, length = 9)
    private String cep;
    @Column(name = "password", nullable = false, length = 200)
    private String password;
    @Column(name = "active", nullable = false)
    private Boolean active;
}
