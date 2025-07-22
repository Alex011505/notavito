package com.lithanarianaren.notavito.controller;

import com.lithanarianaren.notavito.dto.ImageDto;
import com.lithanarianaren.notavito.dto.response.ImageDataResponse;
import com.lithanarianaren.notavito.entity.ImageEntity;
import com.lithanarianaren.notavito.exception.ImageNotFoundException;
import com.lithanarianaren.notavito.service.ImageService;
import com.lithanarianaren.notavito.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @GetMapping(value = "/{stringId}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable String stringId
    ) {
        logger.info("Looking for image {}", stringId);
        try {
            ImageDataResponse img = imageService.getByStringId(stringId);
            logger.info("Image {} found", stringId);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(img.getMimeType()))
                    .body(ImageUtil.decompressImage(img.getData()));
        } catch (ImageNotFoundException e) {
            logger.error("Failed to find image {}: {}", stringId, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageDto> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        logger.info("Uploading new image");
        try {
            ImageDto response = imageService.create(file);
            logger.info("Image uploaded: {}", response.getUrl());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);
        } catch (IOException e) {
            logger.error("Failed to upload image");
            throw e;
        }
    }

    @DeleteMapping("/{stringId}")
    public ResponseEntity<Void> deleteImage(@PathVariable String stringId) {
        logger.info("Deleting image {}", stringId);
        try {
            imageService.deleteByStringId(stringId);
            logger.info("Image {} deleted", stringId);
            return ResponseEntity.noContent().build();
        } catch (ImageNotFoundException e) {
            logger.error("Failed to delete image {}: {}", stringId, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


}
