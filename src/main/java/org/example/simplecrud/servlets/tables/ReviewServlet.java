package org.example.simplecrud.simplecrud.servlets.tables;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.simplecrud.dao.ProductDao;
import org.example.simplecrud.dao.RevievsDao;
import org.example.simplecrud.dao.UserDao;
import org.example.simplecrud.entity.Review;
import org.example.simplecrud.utill.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/reviews")
public class ReviewServlet extends HttpServlet {
    private int id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Review> reviewList = new RevievsDao().findAll().stream().sorted().collect(Collectors.toList());
        req.setAttribute("allReviews", reviewList);
        req.getRequestDispatcher("/WEB-INF/reviews.jsp").forward(req, resp);
        req.setAttribute("check", 0);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = ConnectionManager.get();
        String action = req.getParameter("action");
        if ("Save".equals(action)) {
            Review review = Review.builder()
                    .id(Integer.parseInt(req.getParameter("field1")))
                    .user(new UserDao().findById(Integer.parseInt(req.getParameter("field2")),connection).get())
                    .product(new ProductDao().findById(Integer.parseInt(req.getParameter("field3")),connection).get())
                    .text(req.getParameter("field4"))
                    .rate(Integer.parseInt(req.getParameter("field5")))
                    .build();
            new RevievsDao().save(review, connection);
            List<Review> reviewList = new RevievsDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allReviews", reviewList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/reviews.jsp").forward(req, resp);
        }
        else if ("Delete".equals(action)) {
            String idsString = req.getParameter("dfield");
            boolean reviewDao = new RevievsDao().delete(Integer.parseInt(idsString),connection);
            List<Review> reviewList = new RevievsDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allReviews", reviewList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/reviews.jsp").forward(req, resp);
        }
        else if ("Update".equals(action)) {
            String uString = req.getParameter("ufield");
            id=Integer.parseInt(uString);
            Review user = new RevievsDao().findById(id,connection).get();
            List<Review> reviewList = new RevievsDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("Review", user);
            req.setAttribute("allReviews", reviewList);
            req.setAttribute("check", 1);
            req.getRequestDispatcher("/WEB-INF/reviews.jsp").forward(req, resp);
        }
        else if ("Submit".equals(action)) {
            Review review = Review.builder()
                    .id(id)
                    .user(new UserDao().findById(Integer.parseInt(req.getParameter("field10")),connection).get())
                    .product(new ProductDao().findById(Integer.parseInt(req.getParameter("field11")),connection).get())
                    .text(req.getParameter("field12"))
                    .rate(Integer.parseInt(req.getParameter("field13")))
                    .build();
            int success = new RevievsDao().update(review,connection);
            List<Review> reviewList = new RevievsDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allReviews", reviewList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/reviews.jsp").forward(req, resp);
        }
    }
}
