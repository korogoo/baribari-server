package com.twin.baribari.post.application;

import com.twin.baribari.post.domain.Post;
import com.twin.baribari.post.domain.PostRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post getById(final long id) {
        return postRepository.getById(id);
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public long upload(
        final String title,
        final String body,
        final long memberId,
        final long courseId
    ) {
        final Post post = new Post(
            title,
            body,
            memberId,
            courseId
        );
        return postRepository.save(post);
    }
}
