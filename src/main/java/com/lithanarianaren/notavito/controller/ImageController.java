package com.lithanarianaren.notavito.controller;

import com.lithanarianaren.notavito.dto.ImageDto;
import com.lithanarianaren.notavito.entity.ImageEntity;
import com.lithanarianaren.notavito.exception.ImageNotFoundException;
import com.lithanarianaren.notavito.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;


    private static final Map<String, String> EXT2MIME = Map.of(
            "png",  "image/png",
            "jpg",  "image/jpeg",
            "jpeg", "image/jpeg",
            "gif",  "image/gif",
            "webp", "image/webp"
    );

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "/{stringId}.{extension}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable String stringId,
            @PathVariable String extension
    ) {
        String expectedMime = EXT2MIME.get(extension.toLowerCase());
        if (expectedMime == null) {
            return ResponseEntity.notFound().build();
        }

        ImageEntity img = imageService.getByStringId(stringId);
        if (!expectedMime.equals(img.getMimeType())) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(img.getMimeType()))
                .body(img.getData());
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageDto> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            ImageEntity saved = imageService.create(file);
            ImageDto dto = new ImageDto();
            dto.setId(saved.getId());
            dto.setUrl("/images/" + saved.getStringId() + getExtensionFromMime(saved.getMimeType()));
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private String getExtensionFromMime(String mimeType) {
        return switch (mimeType) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/gif" -> ".gif";
            case "image/webp" -> ".webp";
            default -> "";
        };
    }

    @DeleteMapping("/{stringId}")
    public ResponseEntity<Void> deleteImage(@PathVariable String stringId) {
        try {
            imageService.deleteByStringId(stringId);
            return ResponseEntity.noContent().build();
        } catch (ImageNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
