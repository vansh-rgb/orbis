package com.strink.orbis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
@Getter
@Setter
public class Votes {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String postId;

    private String userId;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    private LocalDateTime createdAt;

    private Boolean isPost;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
