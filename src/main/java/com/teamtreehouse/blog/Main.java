package com.teamtreehouse.blog;

/**
 * Created by Kirill on 9/6/2016.
 */

import com.teamtreehouse.blog.model.BlogEntry;
import com.teamtreehouse.blog.model.BlogEntryDAO;
import com.teamtreehouse.blog.model.Comment;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        staticFileLocation("public");

        BlogEntryDAO dao = new BlogEntryDAO();
        Map<String, Object> model = new HashMap<>();

        get("/", (req, res) -> {
            model.put("blogPosts", dao.findAllEntries());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        before("/new", (req, res) -> {
            if (req.cookie("admin") == null || !req.cookie("admin").equals("admin")) {
                res.redirect("/log-in");
            }
        });

        get("/new", (req, res) -> new ModelAndView(model, "new.hbs"), new HandlebarsTemplateEngine());

        post("/new", (req, res) -> {
            String title = req.queryParams("title");
            String post = req.queryParams("entry");
            BlogEntry blogEntry = new BlogEntry(title, post, "user");
            dao.addEntry(blogEntry);
            res.redirect("/");
            return null;
        });

        get("/log-in", (req, res) -> new ModelAndView(null, "log-in.hbs"), new HandlebarsTemplateEngine());

        post("/log-in", (req, res) -> {
            String admin = req.queryParams("password");
            if (admin.equals("admin")) {
                res.cookie("admin", "admin");
                res.redirect("/new");
            } else {
                res.redirect("/log-in");
            }
            return null;
        });

        get("/:slug", (req, res) -> {
            System.out.println("req params in get:/:slug "+req.params("slug"));
            model.put("post", dao.findEntryBySlug(req.params("slug")));
            return new ModelAndView(model, "detail.hbs");
        }, new HandlebarsTemplateEngine());

        post("/:slug/new-comment", (req, res) -> {
            String url = String.format("/%s",req.params("slug"));
            System.out.println(url);
            BlogEntry entry = dao.findEntryBySlug(req.params("slug"));
            Comment comment = new Comment(req.queryParams("name"), req.queryParams("comment"));
            entry.addComment(comment);
            res.redirect(url);
            return null;
        });
    }
}
