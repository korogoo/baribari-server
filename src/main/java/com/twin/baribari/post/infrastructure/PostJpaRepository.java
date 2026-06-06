package com.twin.baribari.post.infrastructure;

import com.twin.baribari.post.infrastructure.entity.PostJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<PostJpaEntity, Long> {
}
