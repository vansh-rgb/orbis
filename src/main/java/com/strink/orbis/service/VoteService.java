package com.strink.orbis.service;

import com.strink.orbis.dto.VoteDto;
import com.strink.orbis.dto.VoteResponse;
import com.strink.orbis.model.Post;
import com.strink.orbis.model.VoteType;
import com.strink.orbis.model.Votes;
import com.strink.orbis.repository.PostRepository;
import com.strink.orbis.repository.UserRepository;
import com.strink.orbis.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;


    public VoteResponse handleVote(String postId, VoteDto voteDto, String userId) throws Exception {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new Exception("Post not found with id: " + postId));

        Optional<Votes> existingVote = voteRepository.findByUserIdAndPostId(userId, postId);

        if (existingVote.isPresent()) {
            Votes vote = existingVote.get();
            if (voteDto.voteType() == existingVote.get().getVoteType()) {
                return removeVote(vote, post);
            } else {
                return switchVote(vote, post, voteDto.voteType());
            }
        } else {
            return addVote(postId, userId, post, voteDto.voteType());
        }
    }

    private VoteResponse switchVote(Votes vote, Post post, VoteType newVoteType) {
        VoteType oldVote = vote.getVoteType();
        vote.setVoteType(newVoteType);
        voteRepository.save(vote);

        if (oldVote == VoteType.UPVOTE) {
            post.setUpvoteCount(Math.max(0, post.getUpvoteCount() - 1));
            post.setDownvoteCount(post.getDownvoteCount() + 1);
        } else {
            post.setDownvoteCount(Math.max(0, post.getDownvoteCount() - 1));
            post.setUpvoteCount(post.getUpvoteCount() + 1);
        }
        postRepository.save(post);
        return new VoteResponse(newVoteType, post.getUpvoteCount(), post.getDownvoteCount());
    }

    private VoteResponse removeVote(Votes vote, Post post) {
        VoteType removeType = vote.getVoteType();

        voteRepository.delete(vote);

        if (removeType == VoteType.UPVOTE) {
            post.setUpvoteCount(post.getUpvoteCount() - 1);
        } else {
            post.setDownvoteCount(post.getDownvoteCount() - 1);
        }
        postRepository.save(post);

        return new VoteResponse(null, post.getUpvoteCount(), post.getDownvoteCount());
    }

    public VoteResponse addVote(String postId, String userId, Post post, VoteType voteType) throws Exception {
        Votes vote = Votes.builder()
                .voteType(voteType)
                .postId(postId)
                .userId(userId)
                .build();
        voteRepository.save(vote);

        if (voteType == VoteType.UPVOTE) {
            post.setUpvoteCount(post.getUpvoteCount() + 1);
        } else {
            post.setDownvoteCount(post.getDownvoteCount() + 1);
        }

        postRepository.save(post);
        return new VoteResponse(voteType, post.getUpvoteCount(), post.getDownvoteCount());

    }

}
