package com.trilogyed.comment.dao;

import com.trilogyed.comment.model.Comment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CommentDaoJdbcTemplateImplTest {
    @Autowired
    CommentDao commentDao;

    @Before
    public void setUp() throws Exception {
        List<Comment> commentList = commentDao.getAllComments();
        commentList.stream()
                .forEach(comment -> commentDao.deleteComment(comment.getCommentId()));
    }

    @Test
    public void createGetDeleteComment() {

        Comment comment = new Comment();
        comment.setPostId(1);
        comment.setCreateDate(LocalDate.of(2019,3,20));
        comment.setCommenterName("john");
        comment.setComment("comment 1");

        Comment addedcomment = commentDao.createComment(comment);
        Comment commentfromdao = commentDao.getCommentById(comment.getCommentId());
        assertEquals(addedcomment,commentfromdao);

        commentDao.deleteComment(addedcomment.getCommentId());
        commentfromdao = commentDao.getCommentById(comment.getCommentId());
        assertNull(commentfromdao);

    }

//    @Test
//    public void getCommentById() {
//    }

    @Test
    public void getCommentsByPostId() {
        Comment comment = new Comment();
        comment.setPostId(1);
        comment.setCreateDate(LocalDate.of(2019,3,20));
        comment.setCommenterName("john");
        comment.setComment("comment 1");

        commentDao.createComment(comment);

        List<Comment> commentfromdaoList = commentDao.getCommentsByPostId(1);
        assertEquals(1,commentfromdaoList.size());
    }

    @Test
    public void getAllComments() {
        Comment comment = new Comment();
        comment.setPostId(1);
        comment.setCreateDate(LocalDate.of(2019,3,20));
        comment.setCommenterName("john");
        comment.setComment("comment 1");

        commentDao.createComment(comment);

        comment.setPostId(2);
        comment.setCreateDate(LocalDate.of(2019,4,20));
        comment.setCommenterName("kevin");
        comment.setComment("comment 2");

        commentDao.createComment(comment);

        List<Comment> commentfromdao = commentDao.getAllComments();
        assertEquals(2,commentfromdao.size());
    }

    @Test
    public void updateComment() {
        Comment comment = new Comment();
        comment.setPostId(1);
        comment.setCreateDate(LocalDate.of(2019,3,20));
        comment.setCommenterName("john");
        comment.setComment("comment 1");

        commentDao.createComment(comment);

        comment.setPostId(2);
        comment.setCreateDate(LocalDate.of(2019,4,20));
        comment.setCommenterName("kevin");
        comment.setComment("comment 2");

        commentDao.updateComment(comment);

        Comment updatedComment = commentDao.getCommentById(comment.getCommentId());
        assertEquals(comment,updatedComment);



    }

//    @Test
//    public void deleteComment() {
//    }
}