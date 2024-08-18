package org.example.data.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="Abonents")
public class Abonent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 150,nullable = false)
    private String name;

    @Column(length = 19,nullable = false)
    private String phoneNumber;

    @Column(length = 150,nullable = false)
    private String email;

    @Column(length = 500,nullable = false)
    private String address;
}
