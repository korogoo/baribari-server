package com.twin.baribari.member.infrastructure;

import com.twin.baribari.member.infrastructure.entity.MemberJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {

    Optional<MemberJpaEntity> findBySocialId(String socialId);
}
