package com.twin.baribari.member.application;

import com.twin.baribari.member.domain.LoginProvider;
import com.twin.baribari.member.domain.Member;
import com.twin.baribari.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member findBySocialIdOrRegister(
        final String socialId,
        final String name,
        final String email,
        final LoginProvider loginProvider
    ) {
        return memberRepository.findBySocialId(socialId)
            .orElseGet(() -> memberRepository.save(
                new Member(
                    name,
                    email,
                    loginProvider,
                    socialId
                )
            ));
    }
}
