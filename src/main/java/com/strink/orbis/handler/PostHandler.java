package com.strink.orbis.handler;

import com.strink.orbis.dto.CreatePostResponse;
import com.strink.orbis.dto.PostsByUserId;
import com.strink.orbis.dto.PostDto;
import com.strink.orbis.dto.PostResponseDTO;
import com.strink.orbis.model.User;
import com.strink.orbis.service.PostService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.slf4j.SLF4JLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostHandler {

    private final PostService postService;

    Logger logger = LoggerFactory.getLogger(SLF4JLogger.class);

    @PostMapping("/posts")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody PostDto postDto, @AuthenticationPrincipal User user) {
        logger.info(user.getId());
        try {
            PostResponseDTO post = postService.createPost(postDto, user);
            return new ResponseEntity<>(new CreatePostResponse("PostCreated", post), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(new CreatePostResponse(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/posts/{userId}")
    public ResponseEntity<?> getPostFromId(@PathVariable String userId, @RequestBody PostsByUserId postsByUserId) {
        logger.info("Post requested from user: {}", userId);
        try {
            List<PostResponseDTO> posts = postService.getPostFromUserId(userId);
            return new ResponseEntity<>(posts, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getPosts() {
        return null;
    }
}
