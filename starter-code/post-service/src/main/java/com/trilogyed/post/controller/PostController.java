package com.trilogyed.post.controller;

import com.trilogyed.post.dao.PostDao;
import com.trilogyed.post.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PostController {
    @Autowired
    PostDao postdao;
    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody @Valid Post post) {
        return postdao.createPost(post);
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Post getPost(@PathVariable int id) {
        Post post  = postdao.getPostById(id);
        if (post == null)
            throw new IllegalArgumentException("Post does not exist for id " + id);
        return post;
    }

    @RequestMapping(value = "/posts/{poster_name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getPostsByPosterName(@PathVariable String posterName) {
        List<Post> postList = postdao.getPostsByPosterName(posterName);
        if (postList == null)
            throw new IllegalArgumentException("Post does not exist for id " + posterName);
        return postList;
    }
    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getAllPosts() {
        return postdao.getAllPosts();
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePost(@RequestBody @Valid Post post, @PathVariable("id") int id) {
        if (post.getPostID() == 0)
            post.setPostID(id);
        if (id != post.getPostID()) {
            throw new IllegalArgumentException("Post ID provided not available");
        }
        postdao.updatePost(post);
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("id") int id) {
        postdao.deletePost(id);
    }
}
