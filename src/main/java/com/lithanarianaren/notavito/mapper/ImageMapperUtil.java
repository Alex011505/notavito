package com.lithanarianaren.notavito.mapper;

import com.lithanarianaren.notavito.entity.ImageEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ImageMapperUtil {

    public static String buildUrl(final ImageEntity image) {
        return "/images/" + image.getStringId();
    }

}
