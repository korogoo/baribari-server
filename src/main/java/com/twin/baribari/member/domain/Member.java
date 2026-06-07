package com.twin.baribari.member.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class Member {

    private final Long id;
    private final String name;
    private final String email;
    private final LoginProvider loginProvider;
    private final String socialId;

    public Member(
        final Long id,
        final String name,
        final String email,
        final LoginProvider loginProvider,
        final String socialId
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.loginProvider = loginProvider;
        this.socialId = socialId;
        validateArguments();
    }

    private void validateArguments() {
        Objects.requireNonNull(name, "회원 이름은 필수값입니다.");
        Objects.requireNonNull(email, "회원 이메일은 필수값입니다.");
        Objects.requireNonNull(loginProvider, "로그인 제공자는 필수값입니다.");
        Objects.requireNonNull(socialId, "소셜 아이디는 필수값입니다.");
    }

    public Member(
        final String name,
        final String email,
        final LoginProvider loginProvider,
        final String socialId
    ) {
        this(null, name, email, loginProvider, socialId);
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final Member member = (Member) other;
        return id != null && Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
