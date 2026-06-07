package com.twin.baribari.member.infrastructure;

import com.twin.baribari.member.domain.Member;
import com.twin.baribari.member.domain.MemberRepository;
import com.twin.baribari.member.infrastructure.entity.MemberJpaEntity;
import com.twin.baribari.member.infrastructure.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member save(final Member member) {
        final MemberJpaEntity saved = memberJpaRepository.save(MemberMapper.toEntityForSave(member));
        return MemberMapper.toDomain(saved);
    }
}
