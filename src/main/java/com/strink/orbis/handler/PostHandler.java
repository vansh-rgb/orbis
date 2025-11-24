package com.strink.orbis.handler;

import com.strink.orbis.dto.*;
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

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<?> getPostsByUserId(@PathVariable String userId) {
        logger.info("Post requested from user: {}", userId);
        try {
            List<PostResponseDTO> posts = postService.getPostFromUserId(userId);
            return new ResponseEntity<>(posts, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<?> getPostFromId(@PathVariable String postId) {
        logger.info("Post requested from postId: {}", postId);
        try {
            PostResponseDTO post = postService.getPostByPostId(postId);
            return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/posts/nearby")
    public ResponseEntity<?> getPosts(@RequestBody GetPostPayloadDTO postPayloadDTO) {
        try {
            List<PostResponseDTO> posts = postService.getPostsNearby(postPayloadDTO);
            return new ResponseEntity<>(posts, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable String postId) {
        try {
            PostResponseDTO post = postService.deletePost(postId);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
