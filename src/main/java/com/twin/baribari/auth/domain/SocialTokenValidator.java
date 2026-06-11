package com.twin.baribari.auth.domain;

public interface SocialTokenValidator {

    SocialProfile validate(String identityToken);
}
