package com.teamtreehouse.blog.dao;

import com.teamtreehouse.blog.model.BlogEntry;
import com.teamtreehouse.blog.model.NotFoundException;

import java.util.List;
import java.util.Map;

public interface BlogDao {
    boolean addEntry(BlogEntry blogEntry);
    List<BlogEntry> findAllEntries();
    BlogEntry findEntryBySlug(String slug) throws NotFoundException;
    boolean removeEntry(BlogEntry blogEntry);
}
