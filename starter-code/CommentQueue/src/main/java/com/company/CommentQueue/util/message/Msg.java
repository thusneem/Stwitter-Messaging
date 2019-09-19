package com.company.CommentQueue.util.message;

public class Msg {

    private int bookId;

    public Msg() {
    }

    public Msg(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "bookId=" + bookId +
                '}';
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
