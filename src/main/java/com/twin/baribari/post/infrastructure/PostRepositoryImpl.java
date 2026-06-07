package com.twin.baribari.post.infrastructure;

import com.twin.baribari.post.domain.Post;
import com.twin.baribari.post.domain.PostRepository;
import com.twin.baribari.post.infrastructure.entity.PostJpaEntity;
import com.twin.baribari.post.infrastructure.mapper.PostMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    @Override
    public Post save(final Post post) {
        final PostJpaEntity saved = postJpaRepository.save(PostMapper.toEntityForSave(post));
        return PostMapper.toDomain(saved);
    }

    @Override
    public void update(final Post post) {
        final PostJpaEntity entity = postJpaRepository.findById(post.getId())
            .orElseThrow(RuntimeException::new);
        entity.update(post.getTitle(), post.getBody());
    }

    @Override
    public boolean existsById(final long id) {
        return postJpaRepository.existsById(id);
    }

    @Override
    public Post getById(final long id) {
        final PostJpaEntity found = postJpaRepository.findById(id)
            .orElseThrow(RuntimeException::new);
        return PostMapper.toDomain(found);
    }

    @Override
    public List<Post> findAll() {
        return postJpaRepository.findAll().stream()
            .map(PostMapper::toDomain)
            .toList();
    }

    @Override
    public void deleteById(final long id) {
        postJpaRepository.deleteById(id);
    }
}
