package com.lithanarianaren.notavito.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementRequest {
    private Long category;
    @NotBlank(message = "Cannot be blank")
    private String title;
    @NotBlank(message = "Cannot be blank")
    private String description;
    @Min(value = 0, message = "Cannot be negative")
    private BigDecimal price;
    private String imageUrl;

    private boolean isOpen;
}
