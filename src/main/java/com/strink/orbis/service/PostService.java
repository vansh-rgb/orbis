package com.strink.orbis.service;

import com.strink.orbis.dto.PostDto;
import com.strink.orbis.dto.UserDetailsDto;
import com.strink.orbis.model.Post;
import com.strink.orbis.model.User;
import com.strink.orbis.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    private final PostRepository postRepository;

    public Post createPost(PostDto postDto, User user) {
        Post post = new Post();
        post.setTitle(postDto.title());
        post.setCaption(postDto.caption());
        double latitude = postDto.position().latitude();
        double longitude = postDto.position().longitude();
        post.setUserId(user.getId());
        Point locationPoint = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        post.setLocation(locationPoint);
        return postRepository.save(post);
    }
}
