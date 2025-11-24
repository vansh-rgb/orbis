package com.strink.orbis.repository;

import com.strink.orbis.model.Votes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Votes, String> {


    Optional<Votes> findByUserIdAndPostId(String userId, String postId);

    List<Votes> getVotesByPostId(String postId);
}
