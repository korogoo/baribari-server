package com.twin.baribari.fixture;

import com.twin.baribari.auth.domain.RefreshToken;
import com.twin.baribari.auth.infrastructure.entity.RefreshTokenJpaEntity;
import com.twin.baribari.config.TestClockConfig;
import java.time.LocalDateTime;

public class RefreshTokenFixture {

    private static final Long ID = 1L;
    private static final Long MEMBER_ID = 1L;
    private static final String TOKEN = "refresh-token";
    private static final LocalDateTime EXPIRES_AT = LocalDateTime.now(TestClockConfig.FIXED_CLOCK).plusDays(7);

    public static RefreshToken domain() {
        return new RefreshToken(ID, MEMBER_ID, TOKEN, EXPIRES_AT);
    }

    public static RefreshToken domainForSave() {
        return new RefreshToken(MEMBER_ID, TOKEN, EXPIRES_AT);
    }

    public static RefreshTokenJpaEntity entity() {
        return RefreshTokenJpaEntity.builder()
            .memberId(MEMBER_ID)
            .token(TOKEN)
            .expiresAt(EXPIRES_AT)
            .build();
    }
}
