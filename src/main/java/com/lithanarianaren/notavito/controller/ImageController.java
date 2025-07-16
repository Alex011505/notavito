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

import java.util.Map;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping(value = "/{stringId}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable String stringId
    ) {

        ImageDataResponse img = imageService.getByStringId(stringId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(img.getMimeType()))
                .body(ImageUtil.decompressImage(img.getData()));
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageDto> uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
        ImageDto response = imageService.create(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
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
