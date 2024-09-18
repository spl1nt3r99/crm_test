package com.example.crm_test.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable {

    @Serial
    private static final long serialVersionUID = 5429915113788194942L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String domain;

    @Column(nullable = false)
    private String address;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Contact contact;
}
