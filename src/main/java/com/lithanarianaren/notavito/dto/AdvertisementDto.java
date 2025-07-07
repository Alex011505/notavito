package com.lithanarianaren.notavito.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AdvertisementDto {
    private Long id;
    private Long categoryId;
    private Long authorId;

    private String title;
    private String description;
    private BigDecimal price;
    private String imageStringId;
    private String imageUrl;

}
