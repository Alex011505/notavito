package com.lithanarianaren.notavito.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "entities")
public class AdvertisementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_id")
    private Integer id;


}
