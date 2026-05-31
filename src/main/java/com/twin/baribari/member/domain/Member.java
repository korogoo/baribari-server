package com.twin.baribari.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Member {

    private final Long id;
    private final String name;
    private final String email;
    private final LoginProvider loginProvider;
    private final String socialId;

    public Member(final String name, final String email, final LoginProvider loginProvider, final String socialId) {
        this(null, name, email, loginProvider, socialId);
    }
}
