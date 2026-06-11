package com.twin.baribari.auth.domain;

public interface TokenProvider {

    String createAccessToken(Long memberId);

    String createRefreshToken(Long memberId);

    Long extractMemberId(String token);

    boolean isValid(String token);
}
