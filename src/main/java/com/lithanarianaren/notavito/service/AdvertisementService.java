package com.lithanarianaren.notavito.service;

import com.lithanarianaren.notavito.dto.AdvertisementDto;
import com.lithanarianaren.notavito.dto.UserDto;
import com.lithanarianaren.notavito.dto.request.AdvertisementRequest;
import com.lithanarianaren.notavito.entity.AdvertisementEntity;
import com.lithanarianaren.notavito.entity.CategoryEntity;
import com.lithanarianaren.notavito.mapper.AdvertisementMapper;
import com.lithanarianaren.notavito.repository.AdvertisementRepository;
import com.lithanarianaren.notavito.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final AdvertisementMapper advertisementMapper;
    private final UserRepository userRepository;

    public AdvertisementDto create(AdvertisementRequest request) {
        // Автор - текущий пользователь
        UserDto author = userService.getCurrentUser().orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NETWORK_AUTHENTICATION_REQUIRED,
                "Not authenticated"
        ));

        AdvertisementEntity advertisement = advertisementMapper.fromCreateRequest(request);

        advertisement.setAuthor(userRepository.findById(author.getId()).orElse(null));

        advertisementRepository.save(advertisement);

        return advertisementMapper.toDto(advertisement);
    }

    public AdvertisementDto edit(Long id, AdvertisementRequest request) {
        // проверка на авторизацию
        UserDto author = userService.getCurrentUser().orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NETWORK_AUTHENTICATION_REQUIRED,
                "Not authenticated"
        ));
        
        // проверка на существование
        AdvertisementEntity currentAdvertisement = advertisementRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "No such advertisement"
        ));
        
        // проверка на авторство
        if(!Objects.equals(author.getId(), currentAdvertisement.getAuthor().getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Not the author"
            );
        }

        advertisementMapper.updateFromRequest(request, currentAdvertisement);

        AdvertisementEntity saved = advertisementRepository.save(currentAdvertisement);
        return advertisementMapper.toDto(saved);
    }

    public AdvertisementDto getAdvertisement(Long id) {
        AdvertisementEntity advertisement = advertisementRepository.findById(id).orElseThrow(()->new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No such advertisement"
        ));
        return advertisementMapper.toDto(advertisement);
    }

    public void deleteById(Long id) {
        advertisementRepository.deleteById(id);
    }

    public List<AdvertisementDto> findByTitleContainingIgnoreCase(String name) {
        return advertisementMapper.toDtoList(advertisementRepository.findByTitleContainingIgnoreCase(name));
    }

    public List<AdvertisementDto> findAll() {
        return advertisementMapper.toDtoList(advertisementRepository.findAll());
    }
}

