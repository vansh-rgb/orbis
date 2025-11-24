package com.strink.orbis.service;

import com.strink.orbis.model.Comment;
import com.strink.orbis.model.Post;
import com.strink.orbis.repository.CommentRepository;
import com.strink.orbis.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public Comment addComment(String content, String postId, String userId) {
        Post post = postRepository.getPostById(postId);
        if(post==null) {
            return null;
        }
        Comment comment = Comment.builder()
                .content(content)
                .userId(userId)
                .postId(postId)
                .build();
        post.setCommentCount(post.getCommentCount()+1);

        Comment commentResp = commentRepository.save(comment);
        postRepository.save(post);
        return commentResp;

    }

    public Comment deleteComment(String commentId) throws Exception{
        Comment comment = commentRepository.getCommentsById(commentId);
        if(comment==null) {
            throw new Exception("Comment not found!");
        }
        Post post = postRepository.getPostById(comment.getPostId());
        post.setCommentCount(post.getCommentCount()-1);
        commentRepository.deleteById(commentId);

        return comment;

    }

    public void deletePost(String postId) {
        List<Comment> commentsToDelete = commentRepository.getCommentsByPostId(postId);

        commentRepository.deleteAll(commentsToDelete);
    }
}
