package org.example.simplecrud.servlets.tables;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.simplecrud.dao.TagDao;
import org.example.simplecrud.entity.Tag;
import org.example.simplecrud.utill.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/tags")
public class TagServlet extends HttpServlet {
    private int id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Tag> tagList = new TagDao().findAll().stream().sorted().collect(Collectors.toList());
        req.setAttribute("allTags", tagList);
        req.getRequestDispatcher("/WEB-INF/tags.jsp").forward(req, resp);
        req.setAttribute("check", 0);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = ConnectionManager.get();
        String action = req.getParameter("action");
        if ("Save".equals(action)) {
            Tag user = Tag.builder()
                    .id(Integer.parseInt(req.getParameter("field1")))
                    .tag(req.getParameter("field2"))
                    .build();
            new TagDao().save(user, connection);
            List<Tag> tagList = new TagDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allTags", tagList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/tags.jsp").forward(req, resp);
        }
        else if ("Delete".equals(action)) {
            String idsString = req.getParameter("dfield");
            boolean tagDao = new TagDao().delete(Integer.parseInt(idsString),connection);
            List<Tag> tagList = new TagDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allTags", tagList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/tags.jsp").forward(req, resp);
        }
        else if ("Update".equals(action)) {
            String uString = req.getParameter("ufield");
            id=Integer.parseInt(uString);
            Tag tag = new TagDao().findById(id,connection).get();
            List<Tag> tagList = new TagDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("Tag", tag);
            req.setAttribute("allTags", tagList);
            req.setAttribute("check", 1);
            req.getRequestDispatcher("/WEB-INF/tags.jsp").forward(req, resp);
        }
        else if ("Submit".equals(action)) {
            Tag user = Tag.builder()
                    .id(id)
                    .tag(req.getParameter("field10"))
                    .build();
            int success = new TagDao().update(user,connection);
            List<Tag> tagList = new TagDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allTags", tagList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/tags.jsp").forward(req, resp);
        }
    }
}
