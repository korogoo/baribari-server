package com.twin.baribari.auth.infrastructure;

import com.twin.baribari.auth.domain.SocialProfile;
import com.twin.baribari.auth.domain.SocialTokenValidator;
import org.springframework.stereotype.Component;

@Component
public class AppleSocialTokenValidator implements SocialTokenValidator {

    @Override
    public SocialProfile validate(final String identityToken) {
        // TODO: 애플 로그인 연동 시 구현 예정
        return null;
    }
}
