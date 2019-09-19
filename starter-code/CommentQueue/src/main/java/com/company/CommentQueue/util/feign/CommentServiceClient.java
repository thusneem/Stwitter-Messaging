package com.company.CommentQueue.util.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.stream.events.Comment;

@FeignClient(name = "comment-service")
public interface CommentServiceClient {
    @RequestMapping(value = "/comments", method = RequestMethod.POST)
     public Comment createComment( Comment comment) ;

//    @RequestMapping (value = "/notes/{id}", method = RequestMethod.PUT)
//    public void updateNote(@PathVariable("id") int id, Note note);
//
//    @RequestMapping (value = "/notes/book/{book_id}", method = RequestMethod.GET)
//    public List<Note> getNotesByBookId(@PathVariable("book_id") int bookId);
//
//    @RequestMapping (value = "/notes/{id}", method = RequestMethod.DELETE)
//    public void removeNote(@PathVariable("id") int id);
}
