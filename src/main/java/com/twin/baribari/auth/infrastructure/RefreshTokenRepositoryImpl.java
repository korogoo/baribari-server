package com.twin.baribari.auth.infrastructure;

import com.twin.baribari.auth.domain.RefreshToken;
import com.twin.baribari.auth.domain.RefreshTokenRepository;
import com.twin.baribari.auth.infrastructure.entity.RefreshTokenJpaEntity;
import com.twin.baribari.auth.infrastructure.mapper.RefreshTokenMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RefreshTokenJpaRepository jpaRepository;
    private final RefreshTokenMapper mapper;

    @Override
    public RefreshToken save(final RefreshToken refreshToken) {
        final RefreshTokenJpaEntity saved = jpaRepository.findByMemberId(refreshToken.getMemberId())
            .orElseGet(() -> jpaRepository.save(mapper.toEntityForSave(refreshToken)));

        saved.update(
            refreshToken.getToken(),
            refreshToken.getExpiresAt()
        );
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<RefreshToken> findByMemberId(final long memberId) {
        return jpaRepository.findByMemberId(memberId).map(mapper::toDomain);
    }

    @Override
    public void deleteByMemberId(final long memberId) {
        jpaRepository.deleteByMemberId(memberId);
    }
}
