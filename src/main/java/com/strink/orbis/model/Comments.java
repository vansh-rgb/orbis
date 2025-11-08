package com.strink.orbis.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String postId;

    private String userId;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

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
