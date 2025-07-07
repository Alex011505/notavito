package com.lithanarianaren.notavito.service;

import com.lithanarianaren.notavito.dto.AdvertisementDto;
import com.lithanarianaren.notavito.dto.CategoryDto;
import com.lithanarianaren.notavito.entity.AdvertisementEntity;
import com.lithanarianaren.notavito.entity.CategoryEntity;
import com.lithanarianaren.notavito.entity.ImageEntity;
import com.lithanarianaren.notavito.repository.AdvertisementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {

    private final AdvertisementRepository adRepo;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ImageService imageService;

    public AdvertisementService(AdvertisementRepository adRepo,
                                CategoryService categoryService,
                                UserService userService,
                                ImageService imageService) {
        this.adRepo = adRepo;
        this.categoryService = categoryService;
        this.userService = userService;
        this.imageService = imageService;
    }

    public AdvertisementDto save(AdvertisementDto dto) {
        AdvertisementEntity ad = dto.getId() != null
                ? adRepo.findById(dto.getId()).orElseThrow(() ->
                new IllegalArgumentException("AdvertisementEntity not found: " + dto.getId()))
                : new AdvertisementEntity();

        // общие поля
        ad.setTitle(dto.getTitle());
        ad.setDescription(dto.getDescription());
        ad.setPrice(dto.getPrice());


        Optional<CategoryEntity> categoryEntity = categoryService.getCategoryEntity(dto.getCategoryId());
        if(categoryEntity.isPresent()){
            ad.setCategory(categoryEntity.get());
        } else { throw new IllegalArgumentException("Category not found"); }

        // автор
        userService.findUser(dto.getAuthorId(), null, null)
                .ifPresentOrElse(ad::setAuthor,
                        () -> { throw new IllegalArgumentException("Author not found"); });

        // изображение по stringId
        ImageEntity img = imageService.getByStringId(dto.getImageStringId());
        ad.setImage(img);

        AdvertisementEntity saved = adRepo.save(ad);
        return toDto(saved);
    }

    public Optional<AdvertisementDto> findById(Long id) {
        return adRepo.findById(id).map(this::toDto);
    }

    public void deleteById(Long id) {
        adRepo.deleteById(id);
    }

    public List<AdvertisementDto> findByName(String name) {
        return adRepo.findByTitleContainingIgnoreCase(name).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private AdvertisementDto toDto(AdvertisementEntity ad) {
        AdvertisementDto dto = new AdvertisementDto();
        dto.setId(ad.getId());
        dto.setTitle(ad.getTitle());
        dto.setDescription(ad.getDescription());
        dto.setPrice(ad.getPrice());
        dto.setCategoryId(ad.getCategory().getId());
        dto.setAuthorId(ad.getAuthor().getId());

        // отдаём URL
        ImageEntity img = ad.getImage();
        String ext = extensionFromMime(img.getMimeType());
        dto.setImageUrl("/images/" + img.getStringId() + ext);

        return dto;
    }

    // вспомогательный метод для расширения
    private String extensionFromMime(String mime) {
        return switch (mime) {
            case "image/jpeg" -> ".jpg";
            case "image/png"  -> ".png";
            case "image/gif"  -> ".gif";
            case "image/webp" -> ".webp";
            default            -> "";
        };
    }
}

