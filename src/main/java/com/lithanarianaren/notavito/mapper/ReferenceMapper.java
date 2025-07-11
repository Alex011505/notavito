package com.lithanarianaren.notavito.mapper;

import com.lithanarianaren.notavito.entity.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ReferenceMapper {

    @PersistenceContext
    private EntityManager entityManager;

    @ObjectFactory
    public <T extends BaseEntity> T map(final Long id, @TargetType Class<T> type) {
        if(id==null) return null;
        return entityManager.getReference(type, id);
    }


    public Long map(final BaseEntity entity) {
        if(entity==null) return null;
        return entity.getId();
    }

}
