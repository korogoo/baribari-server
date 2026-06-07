package com.twin.baribari.post.application;

import com.twin.baribari.course.domain.exception.CourseNotFoundException;
import com.twin.baribari.post.domain.Post;
import com.twin.baribari.post.domain.PostRepository;
import com.twin.baribari.post.domain.exception.PostNotFoundException;
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

    public void update(final long id, final String title, final String body) {
        final Post post = postRepository.getById(id);
        postRepository.update(post.update(title, body));
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public void delete(final long id) {
        if (!postRepository.existsById(id)) {
            throw new PostNotFoundException(id);
        }
        postRepository.deleteById(id);
    }

    public Post upload(
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
