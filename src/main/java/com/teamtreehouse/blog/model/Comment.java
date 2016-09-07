package com.teamtreehouse.blog.model;

import java.util.Date;

public class Comment {
    private String author;
    private String commentBody;
    private Date creationDate;

    public Comment(String author, String commentBody) {
        this.author = author;
        this.commentBody = commentBody;
        creationDate = new Date();
    }

    public String getAuthor() {
        return author;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
