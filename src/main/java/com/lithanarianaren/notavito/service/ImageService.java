package com.lithanarianaren.notavito.service;

import com.lithanarianaren.notavito.dto.ImageDto;
import com.lithanarianaren.notavito.dto.response.ImageDataResponse;
import com.lithanarianaren.notavito.entity.ImageEntity;
import com.lithanarianaren.notavito.exception.ImageNotFoundException;
import com.lithanarianaren.notavito.mapper.ImageMapper;
import com.lithanarianaren.notavito.repository.ImageRepository;
import com.lithanarianaren.notavito.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();


    public void deleteByStringId(String stringId) {
        ImageEntity image = imageRepository.findByStringId(stringId)
                .orElseThrow(() -> new ImageNotFoundException("Image not found: " + stringId));
        imageRepository.delete(image);
    }


    public ImageDto create(MultipartFile file) throws IOException {
        String stringId;
        do {
            stringId = generateRandomHex(32);
        } while (imageRepository.existsByStringId(stringId));

        ImageEntity entity = ImageEntity.builder()
                .stringId(stringId)
                .mimeType(file.getContentType())
                .data(ImageUtil.compressImage(file.getBytes()))
                .build();

        imageRepository.save(entity);

        return imageMapper.toDto(entity);
    }

    @Transactional
    public ImageDataResponse getByStringId(String stringId) {
        ImageEntity image = imageRepository.findByStringId(stringId)
                .orElseThrow(() -> new ImageNotFoundException("Image not found: " + stringId));
        return imageMapper.toDataResponse(image);

    }

    private String generateRandomHex(int length) {
        byte[] bytes = new byte[length / 2];
        RANDOM.nextBytes(bytes);
        StringBuilder sb = new StringBuilder(length);
        for (byte b : bytes) {
            sb.append(HEX_ARRAY[(b >> 4) & 0x0F])
                    .append(HEX_ARRAY[b & 0x0F]);
        }
        return sb.toString();
    }
}

