package com.twin.baribari.post.presentation;

import com.twin.baribari.global.application.dto.ResourceIdResponse;
import com.twin.baribari.post.application.PostApplicationService;
import com.twin.baribari.post.application.dto.PostDetailResponse;
import com.twin.baribari.post.application.dto.PostSummaryResponse;
import com.twin.baribari.post.presentation.dto.CreatePostRequest;
import com.twin.baribari.post.presentation.dto.UpdatePostRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostApplicationService postApplicationService;

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody UpdatePostRequest request) {
        postApplicationService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailResponse> getById(@PathVariable long id) {
        return ResponseEntity.ok(postApplicationService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<PostSummaryResponse>> getAll() {
        // TODO: 페이징 적용 필요
        return ResponseEntity.ok(postApplicationService.getAll());
    }

    @PostMapping
    public ResponseEntity<ResourceIdResponse> upload(
        @RequestBody CreatePostRequest request,
        @RequestParam Long memberId
    ) {
        final ResourceIdResponse response = postApplicationService.upload(request, memberId);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }
}
