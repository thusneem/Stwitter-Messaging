package com.trilogyed.stwitter.util.feign;

import com.trilogyed.stwitter.dto.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "comment-service")
public interface CommentServiceClient {

    @RequestMapping (value = "/comments", method = RequestMethod.POST)
    public Comment createComment(@RequestBody @Valid Comment comment) ;
}