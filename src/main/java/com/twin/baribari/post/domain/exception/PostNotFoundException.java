package com.twin.baribari.post.domain.exception;

import com.twin.baribari.global.exception.NotFoundException;

public class PostNotFoundException extends NotFoundException {

    public PostNotFoundException(final long id) {
        super("POST_NOT_FOUND", "게시물을 찾을 수 없습니다. (id: " + id + ")");
    }
}
