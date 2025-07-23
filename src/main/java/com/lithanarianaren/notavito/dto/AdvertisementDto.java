package com.lithanarianaren.notavito.dto;

import lombok.*;
import org.hibernate.annotations.AttributeAccessor;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementDto {
    private Long id;
    private Long category;
    private Long author;
    private String title;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private boolean isOpen;
}
