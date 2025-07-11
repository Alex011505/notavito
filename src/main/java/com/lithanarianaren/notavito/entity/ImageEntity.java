package com.lithanarianaren.notavito.entity;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;

import java.util.Arrays;

@Setter
@Getter
@Entity
@Table(name = "images")
public class ImageEntity extends BaseEntity {

    @Column(name = "string_id", nullable = false, unique = true, length = 32)
    private String stringId;

    @Column(name = "mime_type", nullable = false)
    private String mimeType;

    @Lob
    @Column(name = "data", nullable = false, columnDefinition = "BYTEA")
    private byte[] data;
}
