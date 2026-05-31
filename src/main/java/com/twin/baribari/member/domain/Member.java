package com.twin.baribari.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Member {

    private final String name;
    private final String email;
    private final LoginProvider loginProvider;
    private final String socialId;
}
