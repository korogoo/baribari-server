package com.twin.baribari.auth.domain;

import java.util.Optional;

public interface RefreshTokenRepository {

    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findByMemberId(long memberId);

    void deleteByMemberId(long memberId);
}
