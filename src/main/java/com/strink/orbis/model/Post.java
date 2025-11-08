package com.strink.orbis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.awt.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts", indexes = {@Index(name = "idx_post_location", columnList = "location")})
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String userId;

    private String title;

    private String caption;

    private String imgString;

    @Column(name = "location", columnDefinition = "geometry(Point,4326)")
    private Point location;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "upvote_count", nullable = false)
    private Integer upvoteCount = 0;

    @Column(name = "downvote_count", nullable = false)
    private Integer downvoteCount = 0;

    @Column(name = "comment_count", nullable = false)
    private Integer commentCount = 0;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
