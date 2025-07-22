package com.lithanarianaren.notavito.controller;

import com.lithanarianaren.notavito.dto.AdvertisementDto;
import com.lithanarianaren.notavito.dto.request.AdvertisementRequest;
import com.lithanarianaren.notavito.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;
    private static final Logger logger = LoggerFactory.getLogger(AdvertisementController.class);
    
    @PostMapping
    public ResponseEntity<AdvertisementDto> createAdvertisement(@RequestBody AdvertisementRequest advertisementRequest) {
        logger.info("Creating Ad: {}", advertisementRequest.getTitle());
        try {
            AdvertisementDto created = advertisementService.create(advertisementRequest);
            logger.info("Ad {} created by user {}", created.getId(), created.getAuthor());
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (ResponseStatusException e) {
            logger.error("Ad creation failed: {} {}", e.getStatusCode(), e.getMessage());
            throw e;
        }
    }
    @GetMapping
    public ResponseEntity<List<AdvertisementDto>> getAllAdvertisements(@RequestParam(required = false) Long categoryId) {
        logger.info("Retrieving ads of category {}", categoryId);
        try {
            if (categoryId == null)
                return ResponseEntity.ok(advertisementService.findAll());

            return ResponseEntity.ok(advertisementService.findByCategoryId(categoryId));
        } catch (RuntimeException e) {
            logger.error("Could not retrieve ads of category {}: {}", categoryId, e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvertisementDto> editAdvertisement(
            @PathVariable Long id,
            @RequestBody AdvertisementRequest advertisementRequest
    ) {
        logger.info("Editing ad {}", id);
        try {
            AdvertisementDto updated = advertisementService.edit(id, advertisementRequest);
            logger.info("Ad {} edited successfully", id);
            return ResponseEntity.ok(updated);
        } catch (ResponseStatusException e) {
            logger.error("Ad {} edit failed: {} {}", id, e.getStatusCode(), e.getMessage());
            throw e;
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<AdvertisementDto>> searchByTitle(@RequestParam String title) {
        logger.info("Searching for '{}' in ads", title);
        try {
            List<AdvertisementDto> found = advertisementService.findByTitleContainingIgnoreCase(title);
            logger.info("Found {} ads with '{}'", found.size(), title);
            return ResponseEntity.ok(found);
        } catch (ResponseStatusException e) {
            logger.error("Ad search failed: {} {}", e.getStatusCode(), e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementDto> findById(@RequestParam Long id) {
        logger.info("Searching for ad {}", id);
        try {
            AdvertisementDto found = advertisementService.getAdvertisement(id);
            logger.info("Ad {} found", id);
            return ResponseEntity.ok(found);
        } catch (ResponseStatusException e) {
            logger.error("Could not find ad {}: {} {}", id, e.getStatusCode(), e.getMessage());
            throw e;
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable Long id) {
        logger.info("Deleting ad {}", id);
        try {
            advertisementService.deleteById(id);
            logger.info("Ad {} deleted", id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException e) {
            logger.error("Could not delete ad {}: {} {}", id, e.getStatusCode(), e.getMessage());
            throw e;
        }
    }
}
