package com.teamtreehouse.blog.model;

import com.github.slugify.Slugify;
import com.google.common.hash.BloomFilter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogEntry implements Comparable {
    private String title;
    private String post;
    private String creator;
    private Date creationDate;
    private String slug;

    private List<Comment> blogComments;
    private List<String> tags;

    public BlogEntry(String title, String post, String creator){
        this.title = title;
        this.post = post;
        this.creator = creator;
        creationDate = new Date();
        Slugify slugify = new Slugify();
        slug = slugify.slugify(title);

        blogComments = new ArrayList<>();
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<Comment> getBlogComments() {
        return blogComments;
    }

    public String getCreationDate() {
        return creationDate.toString();
    }

    public List<String> getTags() {
        return tags;
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return title;
    }

    public String getPost() {
        return post;
    }

    public String getCreator() {
        return creator;
    }

    public boolean addComment(Comment comment) {
        return blogComments.add(comment);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogEntry blogEntry = (BlogEntry) o;

        if (title != null ? !title.equals(blogEntry.title) : blogEntry.title != null) return false;
        if (post != null ? !post.equals(blogEntry.post) : blogEntry.post != null) return false;
        return creationDate != null ? creationDate.equals(blogEntry.creationDate) : blogEntry.creationDate == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (post != null ? post.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (blogComments != null ? blogComments.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        BlogEntry be = (BlogEntry) o;
        return creationDate.compareTo(be.creationDate);
    }
}
