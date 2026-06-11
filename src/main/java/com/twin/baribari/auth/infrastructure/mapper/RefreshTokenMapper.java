package com.twin.baribari.auth.infrastructure.mapper;

import com.twin.baribari.auth.domain.RefreshToken;
import com.twin.baribari.auth.infrastructure.entity.RefreshTokenJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapper {

    public RefreshToken toDomain(final RefreshTokenJpaEntity entity) {
        return new RefreshToken(
            entity.getId(),
            entity.getMemberId(),
            entity.getToken(),
            entity.getExpiresAt()
        );
    }

    public RefreshTokenJpaEntity toEntityForSave(final RefreshToken domain) {
        return RefreshTokenJpaEntity.builder()
            .memberId(domain.getMemberId())
            .token(domain.getToken())
            .expiresAt(domain.getExpiresAt())
            .build();
    }
}
