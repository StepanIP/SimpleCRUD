package org.example.simplecrud.servlets.tables;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.simplecrud.dao.*;
import org.example.simplecrud.entity.Product;
import org.example.simplecrud.entity.enums.Metal;
import org.example.simplecrud.utill.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private int id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> productList = new ProductDao().findAll().stream().sorted().collect(Collectors.toList());
        req.setAttribute("allProducts", productList);
        req.setAttribute("check", 0);
        req.getRequestDispatcher("/WEB-INF/products.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = ConnectionManager.get();
        String action = req.getParameter("action");
        if ("Save".equals(action)) {
            Product product = Product.builder()
                    .id(Integer.parseInt(req.getParameter("field1")))
                    .name(req.getParameter("field2"))
                    .metal(Metal.valueOf(req.getParameter("field3")))
                    .cost(Integer.parseInt(req.getParameter("field4")))
                    .image(req.getParameter("field5"))
                    .supplier(new SupplierDao().findById(Integer.parseInt(req.getParameter("field6")),connection).get())
                    .build();
            new ProductDao().save(product, connection);
            List<Product> productList = new ProductDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allProducts", productList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/products.jsp").forward(req, resp);
        }
        else if ("Delete".equals(action)) {
            String idsString = req.getParameter("dfield");
            boolean productDao = new ProductDao().delete(Integer.parseInt(idsString),connection);
            List<Product> productList = new ProductDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allProducts", productList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/products.jsp").forward(req, resp);
        }
        else if ("Update".equals(action)) {
            String uString = req.getParameter("ufield");
            id=Integer.parseInt(uString);
            Product product = new ProductDao().findById(id,connection).get();
            List<Product> productList = new ProductDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("Product", product);
            req.setAttribute("allProducts", productList);
            req.setAttribute("check", 1);
            req.getRequestDispatcher("/WEB-INF/products.jsp").forward(req, resp);
        }
        else if ("Submit".equals(action)) {
            Product product = Product.builder()
                    .id(id)
                    .name(req.getParameter("field10"))
                    .metal(Metal.valueOf(req.getParameter("field11")))
                    .cost(Integer.parseInt(req.getParameter("field12")))
                    .image(req.getParameter("field13"))
                    .supplier(new SupplierDao().findById(Integer.parseInt(req.getParameter("field14")),connection).get())
                    .build();
            int success = new ProductDao().update(product,connection);
            List<Product> productList = new ProductDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allProducts", productList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/products.jsp").forward(req, resp);
        }
    }
}
