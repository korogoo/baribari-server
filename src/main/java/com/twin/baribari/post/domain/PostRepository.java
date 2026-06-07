package com.twin.baribari.post.domain;

import java.util.List;

public interface PostRepository {

    Post save(Post post);

    void update(Post post);

    boolean existsById(long id);

    Post getById(long id);

    List<Post> findAll();

    void deleteById(long id);
}
