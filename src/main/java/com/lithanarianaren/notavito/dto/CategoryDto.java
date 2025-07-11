package com.lithanarianaren.notavito.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDto {
    private Long id;
    private String name;
    private Long parent;

}

