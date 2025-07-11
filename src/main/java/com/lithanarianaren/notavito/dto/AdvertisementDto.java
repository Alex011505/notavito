package com.lithanarianaren.notavito.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AdvertisementDto {
    private Long id;
    private Long category;
    private Long author;
    private String title;
    private String description;
    private BigDecimal price;
    private String imageUrl;

}
