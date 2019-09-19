package com.trilogyed.stwitter.service;

import com.trilogyed.stwitter.ViewModel.PostViewModel;
import com.trilogyed.stwitter.dto.Comment;
import com.trilogyed.stwitter.dto.Post;
import com.trilogyed.stwitter.util.feign.CommentServiceClient;
import com.trilogyed.stwitter.util.feign.PostServiceClient;
import com.trilogyed.stwitter.util.feign.PostServiceClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLayer {

    public static final String EXCHANGE = "comment-exchange";

    // First Queue
    public static final String ROUTING_KEY = " comment.create.#" ;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private final CommentServiceClient commentClient;

    @Autowired
    private final PostServiceClient postClient;

    public ServiceLayer(CommentServiceClient commentClient, PostServiceClient postClient, RabbitTemplate rabbitTemplate) {
        this.commentClient = commentClient;
        this.postClient = postClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public Comment saveComment(Comment comment) {
        // Persist Book
        Comment c = new Comment();
        c.setPostId(comment.getPostId());
        c.setCreateDate(comment.getCreateDate());
        c.setCommenterName(comment.getCommenterName());
        c.setComment(comment.getCommenterName());
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY,c);
        System.out.println("Message Sent");
        Comment comment1=commentClient.createComment(c);
        System.out.println(comment1);

        comment1.setCommentId(comment1.getCommentId());
        return comment1;

    }

    public PostViewModel createPost(PostViewModel postViewModel) {
        Post post = new Post();
        post.setPosterName(postViewModel.getPosterName());
        post.setPostDate(postViewModel.getPostDate());
        post.setPost(postViewModel.getPost());

        Post post1 = postClient.createPost(post);
        postViewModel.setPostID(post1.getPostID());
        return postViewModel;
    }

    public PostViewModel getPostById(int id) {
        Post post = postClient.getPost(id);
        return buildPostViewModel(post);
    }

    public List<PostViewModel> getPostsByPosterName(String posterName) {
        List<Post> postList = postClient.getPostsByPosterName(posterName);
        if (postList == null)
            return null;
        else {
            List<PostViewModel> ViewModelList = new ArrayList<>();

            postList.stream()
                    .forEach(p -> {
                        PostViewModel pvm = buildPostViewModel(p);
                        ViewModelList.add(pvm);
                    });
            return ViewModelList;
        }
    }

    // Helper methods
    private PostViewModel buildPostViewModel (Post post){
        PostViewModel pvm = new PostViewModel();
        pvm.setPostID(post.getPostID());
        pvm.setPostDate(post.getPostDate());
        pvm.setPosterName(post.getPosterName());
        pvm.setPost(post.getPost());
        //tvm.setAdvertisement(getAd());

        return pvm;
    }
}
