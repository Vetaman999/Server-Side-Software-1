package com.appnutricare.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

import java.util.Date;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username", nullable = false, length = 16)
    private String username;
    @Column(name = "password", nullable = false, length = 60)
    private String password;
    @Column(name="firstname", nullable = false, length = 50)
    private String firstName;
    @Column(name="lastname", nullable = false, length = 50)
    private String lastName;
    @Column(name="email", nullable = false, length = 50)
    private String email;
    @Column(name="created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}