package com.lithanarianaren.notavito.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ImageDataResponse {
    private String mimeType;
    private byte[] data;
}
