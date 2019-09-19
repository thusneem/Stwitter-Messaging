package com.trilogyed.stwitter.util.feign;

import com.trilogyed.stwitter.dto.Comment;
import com.trilogyed.stwitter.dto.Post;
//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "post-service")
    public interface PostServiceClient {

        @RequestMapping(value = "/posts", method = RequestMethod.POST)
        public Post createPost(@RequestBody @Valid Post post) ;

        @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
        public Post getPost( @PathVariable("id") int id);

        @RequestMapping(value = "/posts/{poster_name}", method = RequestMethod.GET)
        public List<Post> getPostsByPosterName(@PathVariable String posterName);
    }


