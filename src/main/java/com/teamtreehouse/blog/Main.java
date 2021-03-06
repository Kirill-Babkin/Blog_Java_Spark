package com.teamtreehouse.blog;

/**
 * Created by Kirill on 9/6/2016.
 */


import com.teamtreehouse.blog.model.BlogEntry;
import com.teamtreehouse.blog.model.BlogEntryDAO;
import com.teamtreehouse.blog.model.Comment;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        staticFileLocation("public");

        BlogEntryDAO dao = new BlogEntryDAO();
        Map<String, Object> model = new HashMap<>();
        List<String> slug = new ArrayList<>();
        slug.add(null);

        before("/new", (req, res) -> {
            if (req.cookie("admin") == null || !req.cookie("admin").equals("admin")) {
                res.redirect("/log-in");
                halt();
            }
        });

        before("/:slug/delete", (req, res) -> {
            if (req.cookie("admin") == null || !req.cookie("admin").equals("admin")) {
                res.redirect("/log-in");
                halt();
            }
        });

        before("/:slug/edit", (req, res) -> {
            if (req.cookie("admin") == null || !req.cookie("admin").equals("admin")) {
                res.redirect("/log-in");
                halt();
            }
        });

        get("/", (req, res) -> {
            Set<BlogEntry> blogEntries = new TreeSet<BlogEntry>(Collections.reverseOrder());
            blogEntries.addAll(dao.findAllEntries());
            model.put("blogPosts", blogEntries);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/new", (req, res) -> new ModelAndView(model, "new.hbs"), new HandlebarsTemplateEngine());

        get("/:slug/new", (req, res) -> new ModelAndView(model, "new.hbs"), new HandlebarsTemplateEngine());

        post("/new", (req, res) -> {
            String title = req.queryParams("title");
            String post = req.queryParams("entry");
            List<String> tags = new ArrayList<String>();
            BlogEntry blogEntry = new BlogEntry(title, post, "user");
            System.out.println(req.queryParams("tags"));
            if(!req.queryParams("tags").equals("")){
                for(String tag : req.queryParams("tags").split(" ")){
                    System.out.println(tag);
                    tags.add(tag);
                }
                blogEntry.setTags(tags);
            }
            dao.addEntry(blogEntry);
            res.redirect("/");
            return null;
        });

        get("/cancel", (req, res) -> {
            res.redirect("/");
            return null;
        });
        get("/:slug/cancel", (req, res) -> {
            res.redirect("/");
            return null;
        });

        get("/log-in", (req, res) -> new ModelAndView(null, "log-in.hbs"), new HandlebarsTemplateEngine());

        post("/log-in", (req, res) -> {
            System.out.println(req.url());
            String admin = req.queryParams("password");
            if (admin.equals("admin")) {
                res.cookie("admin", "admin");
                res.redirect("/");
            } else {
                res.redirect("/log-in");
            }
            return null;
        });

        get("/:slug", (req, res) -> {
            model.put("post", dao.findEntryBySlug(req.params("slug")));
            return new ModelAndView(model, "detail.hbs");
        }, new HandlebarsTemplateEngine());

        post("/:slug/new-comment", (req, res) -> {
            String url = String.format("/%s",req.params("slug"));
            BlogEntry entry = dao.findEntryBySlug(req.params("slug"));
            Comment comment = new Comment(req.queryParams("name"), req.queryParams("comment"));
            entry.addComment(comment);
            res.redirect(url);
            return null;
        });

        get("/:slug/edit", (req, res) ->{
            slug.set(0,req.params("slug"));
            model.put("post", dao.findEntryBySlug(req.params("slug")));
            return new ModelAndView(model, "edit.hbs");
        }, new HandlebarsTemplateEngine());

        get("/:slug/delete", (req, res) -> {
            dao.removeEntry(dao.findEntryBySlug(req.params("slug")));
            res.redirect("/");
            return null;
        });

        post("/save", (req, res)-> {
            System.out.println("here");
            BlogEntry blogEntry = dao.findEntryBySlug(slug.get(0));
            blogEntry.setPost(req.queryParams("entry"));
            blogEntry.setTitle(req.queryParams("title"));
            res.redirect(String.format("/%s", slug.get(0)));
            return null;
        });
    }
}
