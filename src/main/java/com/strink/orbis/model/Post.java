package com.strink.orbis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.awt.*;

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
}
