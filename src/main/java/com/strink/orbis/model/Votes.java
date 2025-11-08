package com.strink.orbis.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Votes {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NonNull
    private String postId;

    @NonNull
    private String userId;

    @NonNull
    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    private LocalDateTime createdAt;

    private Boolean isPost;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
