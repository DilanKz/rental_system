package com.car_rental.car_rental_system.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Dilan
 * @created 27/02/2024 - 10:12 am
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

}
