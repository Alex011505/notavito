package com.lithanarianaren.notavito.service;

import com.lithanarianaren.notavito.dto.AdvertisementDto;
import com.lithanarianaren.notavito.dto.UserDto;
import com.lithanarianaren.notavito.dto.request.AdvertisementRequest;
import com.lithanarianaren.notavito.entity.AdvertisementEntity;
import com.lithanarianaren.notavito.entity.CategoryEntity;
import com.lithanarianaren.notavito.entity.UserEntity;
import com.lithanarianaren.notavito.enum_.RoleEnum;
import com.lithanarianaren.notavito.mapper.AdvertisementMapper;
import com.lithanarianaren.notavito.mapper.AdvertisementMapperImpl;
import com.lithanarianaren.notavito.repository.AdvertisementRepository;
import com.lithanarianaren.notavito.repository.CategoryRepository;
import com.lithanarianaren.notavito.repository.UserRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AdvertisementServiceTest {

    @Mock
    private AdvertisementRepository advertisementRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private AdvertisementMapperImpl advertisementMapper;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdvertisementService advertisementService;

    @Test
    public void AdvertisementService_Create_ReturnsAdvertisementDto() {
        UserDto user = UserDto.builder().email("test@example.com").role("USER").id(1L).build();
        UserEntity userEntity = UserEntity.builder().email("test@example.com").role(RoleEnum.USER).build();
        userEntity.setId(1L);
        CategoryEntity category = CategoryEntity.builder().name("TEST_CAT").parent(null).build();
        category.setId(1L);
        AdvertisementEntity advertisement = AdvertisementEntity.builder().
                title("TEST AD").category(category).description("TEST").author(userEntity).price(BigDecimal.ONE)
                .imageUrl("/images/404").isOpen(true).build();
        advertisement.setId(1L);
        AdvertisementDto advertisementDto = AdvertisementDto.builder().id(1L).author(1L).category(1L).build();
        AdvertisementRequest advertisementRequest = AdvertisementRequest.builder().title("TEST AD").category(1L).description("TEST").price(BigDecimal.ONE)
                .imageUrl("/images/404").isOpen(true).build();

        when(userService.getCurrentUser()).thenReturn(Optional.of(user));
        when(advertisementRepository.save(any(AdvertisementEntity.class))).thenReturn(advertisement);
        when(advertisementMapper.fromCreateRequest(any(AdvertisementRequest.class))).thenReturn(advertisement);
        when(advertisementMapper.toDto(any(AdvertisementEntity.class))).thenReturn(advertisementDto);
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        AdvertisementDto savedAdvertisement = advertisementService.create(advertisementRequest);

        assertNotNull(savedAdvertisement);
    }

    @Test
    public void AdvertisementService_FindAll_ReturnsAdvertisementDtoList() {
        List<AdvertisementEntity> entityList = List.of(new AdvertisementEntity());
        List<AdvertisementDto> dtoList = List.of(new AdvertisementDto());
        when(advertisementRepository.findAll()).thenReturn(entityList);
        when(advertisementMapper.toDtoList(entityList)).thenReturn(dtoList);
        List<AdvertisementDto> found = advertisementService.findAll();
        assertEquals(dtoList, found);

    }

    @Test
    public void AdvertisementService_Edit_ReturnsAdvertisementDto() {
        UserDto user = UserDto.builder().email("test@example.com").role("USER").id(1L).build();
        UserEntity userEntity = UserEntity.builder().email("test@example.com").role(RoleEnum.USER).build();
        userEntity.setId(1L);
        when(userService.getCurrentUser()).thenReturn(Optional.of(user));

        AdvertisementEntity current = AdvertisementEntity.builder().author(userEntity).build();
        when(advertisementRepository.findById(1L)).thenReturn(Optional.ofNullable(current));
        AdvertisementEntity edited = AdvertisementEntity.builder().author(userEntity).build();
        assert current != null;
        when(advertisementRepository.save(current)).thenReturn(edited);
        AdvertisementRequest request = new AdvertisementRequest();

        AdvertisementDto response = new AdvertisementDto();
        when(advertisementMapper.toDto(edited)).thenReturn(response);

        CategoryEntity category = new CategoryEntity();
        when(categoryRepository.findById(any())).thenReturn(Optional.of(category));


        AdvertisementDto dto = advertisementService.edit(1L, request);

        assertEquals(dto, response);
    }

    @Test
    public void AdvertisementService_FindById_ReturnsAdvertisementDto() {
        AdvertisementEntity entity = new AdvertisementEntity();
        AdvertisementDto dto=new AdvertisementDto();
        when(advertisementRepository.findById(any())).thenReturn(Optional.of(entity));
        when(advertisementMapper.toDto(entity)).thenReturn(dto);
        AdvertisementDto found = advertisementService.getAdvertisement(1L);
        assertEquals(dto, found);
    }

    @Test
    public void AdvertisementService_DeleteById_ReturnsVoid() {
        UserDto user = UserDto.builder().email("test@example.com").role("USER").id(1L).build();
        UserEntity userEntity = UserEntity.builder().email("test@example.com").role(RoleEnum.USER).build();
        userEntity.setId(1L);
        when(userService.getCurrentUser()).thenReturn(Optional.of(user));

        AdvertisementEntity current = AdvertisementEntity.builder().author(userEntity).build();
        when(advertisementRepository.findById(1L)).thenReturn(Optional.ofNullable(current));

        doNothing().when(advertisementRepository).deleteById(any());

        advertisementService.deleteById(1L);

        verify(advertisementRepository).deleteById(1L);
    }


}
