package com.trilogyed.post.dao;

import com.trilogyed.post.model.Post;
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
public class PostDaoJdbcTemplateImplTest {

    @Autowired
    PostDao postDao;

    @Before
    public void setUp() throws Exception {
        List<Post> postList = postDao.getAllPosts();
        postList.stream()
                .forEach(post -> postDao.deletePost(post.getPostID()));
    }

    @Test
    public void createGetDeletePost() {

        Post post = new Post();
        post.setPostID(1);
        post.setPostDate(LocalDate.of(2019, 3, 20));
        post.setPosterName("john");
        post.setPost("post 1");

        Post addedPost = postDao.createPost(post);
        Post postfromdao = postDao.getPostById(post.getPostID());
        assertEquals(addedPost, postfromdao);

        postDao.deletePost(addedPost.getPostID());
        postfromdao = postDao.getPostById(post.getPostID());
        assertNull(postfromdao);

    }

    @Test
    public void getPostsByPosterName() {
        Post post = new Post();

        post.setPostDate(LocalDate.of(2019, 3, 20));
        post.setPosterName("john");
        post.setPost("post 1");

        postDao.createPost(post);

        List<Post> postfromdao = postDao.getPostsByPosterName(post.getPosterName());
        assertEquals(1, postfromdao.size());

    }


    @Test
    public void getAllPosts() {
        Post post = new Post();

        post.setPostDate(LocalDate.of(2019, 3, 20));
        post.setPosterName("john");
        post.setPost("post 1");

        postDao.createPost(post);

        List<Post> postfromdao = postDao.getAllPosts();
        assertEquals(1, postfromdao.size());
    }

    @Test
    public void updateComment() {
        Post post = new Post();

        post.setPostDate(LocalDate.of(2019, 3, 20));
        post.setPosterName("john");
        post.setPost("post 1");

        postDao.createPost(post);


        post.setPostDate(LocalDate.of(2018, 2, 20));
        post.setPosterName("smith");
        post.setPost("post 2");

        postDao.updatePost(post);

        Post updatedPost = postDao.getPostById(post.getPostID());
        assertEquals(post, updatedPost);


    }
}