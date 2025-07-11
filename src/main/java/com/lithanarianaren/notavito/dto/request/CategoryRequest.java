package com.lithanarianaren.notavito.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotBlank (message = "Cannot be blank")
    private String name;
    private Long parent;
}
