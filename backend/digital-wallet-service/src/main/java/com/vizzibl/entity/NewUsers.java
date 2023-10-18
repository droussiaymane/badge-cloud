package com.vizzibl.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String userName;
    String firstName;
    String lastName;
    @Column(unique = true)
    String userId;
    String batchId;
    String email;
    String category;
    String tenantId;
    Boolean registrationStatus;
    Boolean emailStatus;

}
