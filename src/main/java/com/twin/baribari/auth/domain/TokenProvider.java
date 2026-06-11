package com.twin.baribari.auth.domain;

public interface TokenProvider {

    String createAccessToken(long memberId);

    String createRefreshToken(long memberId);

    Long extractMemberId(String token);

    boolean isValid(String token);
}
