package com.twin.baribari.member.infrastructure;

import com.twin.baribari.member.domain.Member;
import com.twin.baribari.member.domain.MemberRepository;
import com.twin.baribari.member.infrastructure.entity.MemberJpaEntity;
import com.twin.baribari.member.infrastructure.mapper.MemberMapper;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberMapper mapper;

    @Override
    public Member save(final Member member) {
        final MemberJpaEntity saved = memberJpaRepository.save(mapper.toEntityForSave(member));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Member> findBySocialId(final String socialId) {
        return memberJpaRepository.findBySocialId(socialId)
            .map(mapper::toDomain);
    }
}
