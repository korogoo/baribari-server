package com.twin.baribari.member.domain;

import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findBySocialId(String socialId);
}
