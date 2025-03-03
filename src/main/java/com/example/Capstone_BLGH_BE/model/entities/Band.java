package com.example.Capstone_BLGH_BE.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bands")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Band {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String nomeBand;
    @Column(nullable = false)
    private String bio;
    @Column(nullable = false)
    private String genereMusicale;
    @Column(nullable = false)
    private String fotoBand;
    private String sitoWeb;
    private String instagram;
    private String facebook;
    private String spotify;
    private String soundcloud;
    private String youtube;


}
