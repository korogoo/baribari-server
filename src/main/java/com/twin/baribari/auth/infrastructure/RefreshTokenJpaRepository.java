package com.twin.baribari.auth.infrastructure;

import com.twin.baribari.auth.infrastructure.entity.RefreshTokenJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenJpaEntity, Long> {

    Optional<RefreshTokenJpaEntity> findByMemberId(long memberId);

    void deleteByMemberId(long memberId);
}
