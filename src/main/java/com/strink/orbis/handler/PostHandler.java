package com.strink.orbis.handler;

import com.strink.orbis.dto.CreatePostResponse;
import com.strink.orbis.dto.PostDto;
import com.strink.orbis.model.Post;
import com.strink.orbis.model.User;
import com.strink.orbis.service.PostService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.slf4j.SLF4JLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostHandler {

    private final PostService postService;

    Logger logger = LoggerFactory.getLogger(SLF4JLogger.class);

    @PostMapping("/post")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody PostDto postDto, @AuthenticationPrincipal User user) {
        logger.info(user.getId());
        try {
            Post post = postService.createPost(postDto, user);
            return new ResponseEntity<>(new CreatePostResponse("PostCreated", post), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(new CreatePostResponse(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
