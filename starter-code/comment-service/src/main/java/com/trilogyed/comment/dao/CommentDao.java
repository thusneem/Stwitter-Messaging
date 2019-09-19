package com.trilogyed.comment.dao;

import com.trilogyed.comment.model.Comment;

import java.util.List;

public interface CommentDao {
    Comment createComment(Comment comment);

    Comment getCommentById(int id);

    List<Comment> getCommentsByPostId(int postId);

    List<Comment> getAllComments();

    void updateComment(Comment comment);

    void deleteComment(int id);

}
