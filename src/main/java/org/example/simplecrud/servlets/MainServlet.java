package org.example.simplecrud.servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
@WebServlet("/index")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> tables = List.of(
                "Users",
                "Detail Information",
                "Cart",
                "Products",
                "Suppliers",
                "Orders",
                "Reviews",
                "Categories",
                "Tags",
                "Promotions",
                "Payments",
                "Wishlists"
        );

        List<String> urls = List.of(
                "/user",
                "/detail_informations",
                "/carts",
                "/products",
                "/suppliers",
                "/orders",
                "/reviews",
                "/categories",
                "/tags",
                "/promotions",
                "/payments",
                "/wishlists"
        );
        req.setAttribute("allTables", tables);
        req.setAttribute("allUrls", urls);
        req.getRequestDispatcher("/WEB-INF/main.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
