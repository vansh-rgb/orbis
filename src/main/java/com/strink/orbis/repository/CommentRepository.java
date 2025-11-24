package com.strink.orbis.repository;

import com.strink.orbis.model.Comment;
import com.strink.orbis.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    Comment getCommentsById(String commentId);

    List<Comment> getCommentsByPostId(String postId);
}
