package com.lithanarianaren.notavito.entity;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;

import java.util.Arrays;

@Getter
@Entity
@Table(name = "images")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "string_id", nullable = false, unique = true, length = 32)
    private String stringId;

    @Setter
    @Column(name = "mime_type", nullable = false)
    private String mimeType;

    @Setter
    @Lob
    @Column(name = "data", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] data;
}
