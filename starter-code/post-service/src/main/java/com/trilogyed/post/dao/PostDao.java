package com.trilogyed.post.dao;

import com.trilogyed.post.model.Post;

import java.util.List;

public interface PostDao {
    Post createPost(Post post);

    Post getPostById(int id);

   List<Post> getPostsByPosterName(String posterName);

    List<Post> getAllPosts();

    void updatePost(Post post);

    void deletePost(int id);
}
