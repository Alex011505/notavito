package com.lithanarianaren.notavito.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
public class ImageEntity extends BaseEntity {

    @Column(name = "string_id", nullable = false, unique = true, length = 32)
    private String stringId;

    @Column(name = "mime_type", nullable = false)
    private String mimeType;

    @Lob
    @Column(name = "data", nullable = false)
    private byte[] data;
}
