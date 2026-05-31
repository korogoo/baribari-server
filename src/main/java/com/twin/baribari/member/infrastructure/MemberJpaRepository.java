package com.twin.baribari.member.infrastructure;

import com.twin.baribari.member.infrastructure.entity.MemberJpaEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, UUID> {
}
