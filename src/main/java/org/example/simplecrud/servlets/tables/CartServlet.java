package org.example.simplecrud.servlets.tables;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.simplecrud.dao.CartDao;
import org.example.simplecrud.entity.Cart;
import org.example.simplecrud.utill.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/carts")
public class CartServlet extends HttpServlet {
    private int id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Cart> cartList = new CartDao().findAll().stream().sorted().collect(Collectors.toList());
        req.setAttribute("allCarts", cartList);
        req.getRequestDispatcher("/WEB-INF/carts.jsp").forward(req, resp);
        req.setAttribute("check", 0);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = ConnectionManager.get();
        String action = req.getParameter("action");
        if ("Save".equals(action)) {
            Cart cart = Cart.builder()
                    .id(Integer.parseInt(req.getParameter("field1")))
                    .totalCost(Float.parseFloat(req.getParameter("field2")))
                    .totalAmount(Integer.parseInt(req.getParameter("field3")))
                    .products(null)
                    .build();
            new CartDao().save(cart, connection);
            List<Cart> cartList = new CartDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allCarts", cartList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/carts.jsp").forward(req, resp);
        }
        else if ("Delete".equals(action)) {
            String idsString = req.getParameter("dfield");
            boolean cartDao = new CartDao().delete(Integer.parseInt(idsString),connection);
            List<Cart> cartList = new CartDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allCarts", cartList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/carts.jsp").forward(req, resp);
        }
        else if ("Update".equals(action)) {
            String uString = req.getParameter("ufield");
            id=Integer.parseInt(uString);
            Cart cart = new CartDao().findById(id,connection).get();
            List<Cart> cartList = new CartDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("Cart", cart);
            req.setAttribute("allCarts", cartList);
            req.setAttribute("check", 1);
            req.getRequestDispatcher("/WEB-INF/carts.jsp").forward(req, resp);
        }
        else if ("Submit".equals(action)) {
            Cart cart = Cart.builder()
                    .id(id)
                    .totalCost(Float.parseFloat(req.getParameter("field10")))
                    .totalAmount(Integer.parseInt(req.getParameter("field11")))
                    .products(null)
                    .build();
            int success = new CartDao().update(cart,connection);
            List<Cart> cartList = new CartDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allCarts", cartList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/carts.jsp").forward(req, resp);
        }
    }
}
