package com.lithanarianaren.notavito.service;

import com.lithanarianaren.notavito.entity.ImageEntity;
import com.lithanarianaren.notavito.exception.ImageNotFoundException;
import com.lithanarianaren.notavito.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.security.SecureRandom;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void deleteByStringId(String stringId) {
        ImageEntity image = imageRepository.findByStringId(stringId)
                .orElseThrow(() -> new ImageNotFoundException("Image not found: " + stringId));
        imageRepository.delete(image);
    }


    public ImageEntity create(MultipartFile file) throws Exception {
        String stringId;
        do {
            stringId = generateRandomHex(32);
        } while (imageRepository.existsByStringId(stringId));

        ImageEntity entity = new ImageEntity();
        entity.setStringId(stringId);
        entity.setMimeType(file.getContentType());
        entity.setData(file.getBytes());
        return imageRepository.save(entity);
    }

    public ImageEntity getByStringId(String stringId) {
        return imageRepository.findByStringId(stringId)
                .orElseThrow(() -> new ImageNotFoundException("Image not found: " + stringId));
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

