package com.twin.baribari.auth.application;

import com.twin.baribari.auth.application.dto.TokenResponse;
import com.twin.baribari.auth.domain.SocialProfile;
import com.twin.baribari.auth.domain.SocialTokenValidator;
import com.twin.baribari.member.application.MemberService;
import com.twin.baribari.member.domain.LoginProvider;
import com.twin.baribari.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthApplicationService {

    private final MemberService memberService;
    private final AuthService authService;
    private final SocialTokenValidator socialTokenValidator;

    @Transactional
    public TokenResponse login(final String identityToken, final String fullName) {
        final SocialProfile profile = socialTokenValidator.validate(identityToken);

        final Member member = memberService.findBySocialIdOrRegister(
            profile.socialId(),
            fullName,
            profile.email(),
            LoginProvider.APPLE
        );

        final String accessToken = authService.issueAccessToken(member.getId());
        final String refreshToken = authService.issueRefreshToken(member.getId());
        return new TokenResponse(accessToken, refreshToken);
    }

    @Transactional
    public TokenResponse reissue(final String refreshToken) {
        return authService.reissueTokens(refreshToken);
    }

    @Transactional
    public void logout(final long memberId) {
        authService.revokeToken(memberId);
    }
}
