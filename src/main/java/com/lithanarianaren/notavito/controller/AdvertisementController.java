package com.lithanarianaren.notavito.controller;

import com.lithanarianaren.notavito.dto.AdvertisementDto;
import com.lithanarianaren.notavito.dto.request.AdvertisementRequest;
import com.lithanarianaren.notavito.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;
    
    @PostMapping
    public ResponseEntity<AdvertisementDto> createAdvertisement(@RequestBody AdvertisementRequest advertisementRequest) {
        AdvertisementDto created = advertisementService.create(advertisementRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @GetMapping
    public ResponseEntity<List<AdvertisementDto>> getAllAdvertisements() {
        return ResponseEntity.ok(advertisementService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvertisementDto> editAdvertisement(
            @PathVariable Long id,
            @RequestBody AdvertisementRequest advertisementRequest
    ) {
        AdvertisementDto updated = advertisementService.edit(id, advertisementRequest);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AdvertisementDto>> searchByTitle(@RequestParam String title) {
        List<AdvertisementDto> found = advertisementService.findByTitleContainingIgnoreCase(title);
        return ResponseEntity.ok(found);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementDto> findById(@RequestParam Long id) {
        AdvertisementDto found = advertisementService.getAdvertisement(id);
        return ResponseEntity.ok(found);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable Long id) {
        advertisementService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
