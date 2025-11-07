package com.strink.orbis.repository;

import com.strink.orbis.dto.PostResponseDTO;
import com.strink.orbis.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.locationtech.jts.geom.Point;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

    List<Post> getPostByUserId(String userId);

    Post getPostById(String id);

    @Query(value = "SELECT * FROM posts p " +
            "WHERE ST_DWithin(p.location::geography, CAST(:point AS geography), :radiusInMeters)",
            nativeQuery = true)
    List<Post> findPostsNearby(@Param("point") Point point, @Param("radiusInMeters") double radiusInMeters);

}
