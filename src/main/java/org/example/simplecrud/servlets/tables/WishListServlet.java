package org.example.simplecrud.servlets.tables;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.simplecrud.dao.ProductDao;
import org.example.simplecrud.dao.UserDao;
import org.example.simplecrud.dao.WishListDao;
import org.example.simplecrud.entity.WishList;
import org.example.simplecrud.utill.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/wishlists")
public class WishListServlet extends HttpServlet {
    private int id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<WishList> wishListList = new WishListDao().findAll().stream().sorted().collect(Collectors.toList());
        req.setAttribute("allWL", wishListList);
        req.getRequestDispatcher("/WEB-INF/wishlists.jsp").forward(req, resp);
        req.setAttribute("check", 0);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = ConnectionManager.get();
        String action = req.getParameter("action");
        if ("Save".equals(action)) {
            WishList wishList = WishList.builder()
                    .id(Integer.parseInt(req.getParameter("field1")))
                    .product(new ProductDao().findById(Integer.parseInt(req.getParameter("field2")),connection).get())
                    .user(new UserDao().findById(Integer.parseInt(req.getParameter("field3")),connection).get())
                    .build();
            new WishListDao().save(wishList, connection);
            List<WishList> wishListList = new WishListDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allWL", wishListList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/wishlists.jsp").forward(req, resp);
        }
        else if ("Delete".equals(action)) {
            String idsString = req.getParameter("dfield");
            boolean wishListDao = new WishListDao().delete(Integer.parseInt(idsString),connection);
            List<WishList> wishListList = new WishListDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allWL", wishListList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/wishlists.jsp").forward(req, resp);
        }
        else if ("Update".equals(action)) {
            String uString = req.getParameter("ufield");
            id=Integer.parseInt(uString);
            WishList wishList = new WishListDao().findById(id,connection).get();
            List<WishList> wishListList = new WishListDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("WishList", wishList);
            req.setAttribute("allWL", wishListList);
            req.setAttribute("check", 1);
            req.getRequestDispatcher("/WEB-INF/wishlists.jsp").forward(req, resp);
        }
        else if ("Submit".equals(action)) {
            WishList wishList = WishList.builder()
                    .id(id)
                    .product(new ProductDao().findById(Integer.parseInt(req.getParameter("field10")),connection).get())
                    .user(new UserDao().findById(Integer.parseInt(req.getParameter("field11")),connection).get())
                    .build();
            int success = new WishListDao().update(wishList,connection);
            List<WishList> wishListList = new WishListDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allWL", wishListList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/wishlists.jsp").forward(req, resp);
        }
    }
}
