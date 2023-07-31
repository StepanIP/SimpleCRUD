package org.example.simplecrud.servlets.tables;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.simplecrud.dao.*;
import org.example.simplecrud.entity.Order;
import org.example.simplecrud.utill.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {
    private int id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orderList = new OrderDao().findAll().stream().sorted().collect(Collectors.toList());
        req.setAttribute("allOrders", orderList);
        req.getRequestDispatcher("/WEB-INF/orders.jsp").forward(req, resp);
        req.setAttribute("check", 0);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = ConnectionManager.get();
        String action = req.getParameter("action");
        if ("Save".equals(action)) {
            Order user = Order.builder()
                    .id(Integer.parseInt(req.getParameter("field1")))
                    .state(req.getParameter("field2"))
                    .dateOrdered(LocalDate.parse(req.getParameter("field3")))
                    .dateShifted(LocalDate.parse(req.getParameter("field4")))
                    .product(new ProductDao().findById(Integer.parseInt(req.getParameter("field5")),connection).get())
                    .user(new UserDao().findById(Integer.parseInt(req.getParameter("field6")),connection).get())
                    .build();
            new OrderDao().save(user, connection);
            List<Order> orderList = new OrderDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allOrders", orderList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/orders.jsp").forward(req, resp);
        }
        else if ("Delete".equals(action)) {
            String idsString = req.getParameter("dfield");
            boolean orderDao = new OrderDao().delete(Integer.parseInt(idsString),connection);
            List<Order> orderList = new OrderDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allOrders", orderList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/orders.jsp").forward(req, resp);
        }
        else if ("Update".equals(action)) {
            String uString = req.getParameter("ufield");
            id=Integer.parseInt(uString);
            Order user = new OrderDao().findById(id,connection).get();
            List<Order> orderList = new OrderDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("Order", user);
            req.setAttribute("allOrders", orderList);
            req.setAttribute("check", 1);
            req.getRequestDispatcher("/WEB-INF/orders.jsp").forward(req, resp);
        }
        else if ("Submit".equals(action)) {
            Order user = Order.builder()
                    .id(id)
                    .state(req.getParameter("field10"))
                    .dateOrdered(LocalDate.parse(req.getParameter("field11")))
                    .dateShifted(LocalDate.parse(req.getParameter("field12")))
                    .product(new ProductDao().findById(Integer.parseInt(req.getParameter("field13")),connection).get())
                    .user(new UserDao().findById(Integer.parseInt(req.getParameter("field14")),connection).get())
                    .build();
            int success = new OrderDao().update(user,connection);
            List<Order> orderList = new OrderDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allOrders", orderList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/orders.jsp").forward(req, resp);
        }
    }
}
