package com.teamtreehouse.blog.model;

import com.teamtreehouse.blog.dao.BlogDao;

import java.util.*;

/**
 * Created by Kirill on 9/6/2016.
 */
public class BlogEntryDAO implements BlogDao {
    private List<BlogEntry> blogEntries;

    public BlogEntryDAO() {
        this.blogEntries = new ArrayList<>();
    }

    @Override
    public boolean addEntry(BlogEntry blogEntry) {
        return blogEntries.add(blogEntry);
    }

    @Override
    public List<BlogEntry> findAllEntries() {
        return new ArrayList<>(blogEntries);
    }

    @Override
    public BlogEntry findEntryBySlug(String slug) throws NotFoundException {

            return blogEntries.stream()
                    .filter(idea -> idea.getSlug().equals(slug))
                    .findFirst()
                    .orElseThrow(NotFoundException::new);
    }

    @Override
    public boolean removeEntry(BlogEntry blogEntry) {
        return blogEntries.remove(blogEntry);
    }
    
}
