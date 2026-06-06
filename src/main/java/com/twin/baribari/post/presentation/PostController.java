package com.twin.baribari.post.presentation;

import com.twin.baribari.global.application.dto.ResourceIdResponse;
import com.twin.baribari.post.application.PostApplicationService;
import com.twin.baribari.post.presentation.dto.CreatePostRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
