package com.twin.baribari.member.infrastructure.mapper;

import com.twin.baribari.member.domain.Member;
import com.twin.baribari.member.infrastructure.entity.MemberJpaEntity;

public class MemberMapper {

    public static Member toDomain(final MemberJpaEntity entity) {
        return new Member(
            entity.getId(),
            entity.getName(),
            entity.getEmail(),
            entity.getLoginProvider(),
            entity.getSocialId()
        );
    }

    public static MemberJpaEntity toEntityForSave(final Member domain) {
        return MemberJpaEntity.builder()
            .name(domain.getName())
            .email(domain.getEmail())
            .loginProvider(domain.getLoginProvider())
            .socialId(domain.getSocialId())
            .build();
    }
}
