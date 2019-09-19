package com.company.CommentQueue;

import com.company.CommentQueue.util.feign.CommentServiceClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;

@Service
public class MessageListener {

    @Autowired
    private final CommentServiceClient client;

    public MessageListener(CommentServiceClient client) {
        this.client = client;
    }

    @RabbitListener(queues = CommentQueueApplication.QUEUE_NAME)
    public void receiveMessage(Comment comment) {

            System.out.println("Sending to Note Service to create "+comment.toString());
            client.createComment(comment);
    }

//    @RabbitListener(queues = NoteQueueApplication.DELETE_QUEUE_NAME)
//    public void receiveBookId(Msg msg) {
//        System.out.println("Received: "+msg);
//        System.out.println("Sending for deletion...");
//
//        // Getting All Notes for Given Book Id
//        List<Note> noteList = client.getNotesByBookId(msg.getBookId());
//
//        //Deleting Notes
//        noteList.stream().forEach(note -> {
//            client.removeNote(note.getNoteId());
//            System.out.println("Note "+note.getNoteId()+" deleted successfully");
//        });
//    }

    // Simulating Delay
    private void simulateSlowService() {
        try {
            long time = 1000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
