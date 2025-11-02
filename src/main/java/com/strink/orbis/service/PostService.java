package com.strink.orbis.service;

import com.strink.orbis.dto.PostDto;
import com.strink.orbis.dto.PostResponseDTO;
import com.strink.orbis.model.Post;
import com.strink.orbis.model.User;
import com.strink.orbis.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    private final PostRepository postRepository;

    public PostResponseDTO createPost(PostDto postDto, User user) {
        Post post = new Post();
        post.setTitle(postDto.title());
        post.setCaption(postDto.caption());
        double latitude = postDto.position().latitude();
        double longitude = postDto.position().longitude();
        post.setUserId(user.getId());
        Point locationPoint = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        post.setLocation(locationPoint);
        Post responsePost = postRepository.save(post);
        return new PostResponseDTO(responsePost.getId(), responsePost.getTitle(), responsePost.getCaption());
    }

    public List<PostResponseDTO> getPostFromUserId(String userId) {
        List<Post> posts = postRepository.getPostByUserId(userId);
        List<PostResponseDTO> postResponseDTOS = new ArrayList<>();

        for(Post post: posts) {
            PostResponseDTO postResponseDTO = new PostResponseDTO(post.getId(), post.getTitle(), post.getCaption());
            postResponseDTOS.add(postResponseDTO);
        }
        return postResponseDTOS;
    }

    public PostResponseDTO getPostByPostId(String postId) {
        Post post = postRepository.getPostById(postId);

        return new PostResponseDTO(post.getId(), post.getTitle(), post.getCaption());
    }
}
