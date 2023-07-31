package org.example.simplecrud.servlets.tables;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.simplecrud.dao.ProductDao;
import org.example.simplecrud.dao.PromotionDao;
import org.example.simplecrud.entity.Promotion;
import org.example.simplecrud.utill.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/promotions")
public class PromotionServlet extends HttpServlet {

    private int id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Promotion> promotionList = new PromotionDao().findAll().stream().sorted().collect(Collectors.toList());
        req.setAttribute("allPromotions", promotionList);
        req.getRequestDispatcher("/WEB-INF/promotions.jsp").forward(req, resp);
        req.setAttribute("check", 0);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = ConnectionManager.get();
        String action = req.getParameter("action");
        if ("Save".equals(action)) {
            Promotion promotion = Promotion.builder()
                    .id(Integer.parseInt(req.getParameter("field1")))
                    .startDate(LocalDate.parse(req.getParameter("field2")))
                    .endDate(LocalDate.parse(req.getParameter("field3")))
                    .product(new ProductDao().findById(Integer.parseInt(req.getParameter("field4")),connection).get())
                    .discount(Double.parseDouble(req.getParameter("field5")))
                    .build();
            new PromotionDao().save(promotion, connection);
            List<Promotion> promotionList = new PromotionDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allPromotions", promotionList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/promotions.jsp").forward(req, resp);
        }
        else if ("Delete".equals(action)) {
            String idsString = req.getParameter("dfield");
            boolean promotionDao = new PromotionDao().delete(Integer.parseInt(idsString),connection);
            List<Promotion> promotionList = new PromotionDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allPromotions", promotionList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/promotions.jsp").forward(req, resp);
        }
        else if ("Update".equals(action)) {
            String uString = req.getParameter("ufield");
            id=Integer.parseInt(uString);
            Promotion promotion = new PromotionDao().findById(id,connection).get();
            List<Promotion> promotionList = new PromotionDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("Promotion", promotion);
            req.setAttribute("allPromotions", promotionList);
            req.setAttribute("check", 1);
            req.getRequestDispatcher("/WEB-INF/promotions.jsp").forward(req, resp);
        }
        else if ("Submit".equals(action)) {
            Promotion promotion = Promotion.builder()
                    .id(id)
                    .startDate(LocalDate.parse(req.getParameter("field10")))
                    .endDate(LocalDate.parse(req.getParameter("field11")))
                    .product(new ProductDao().findById(Integer.parseInt(req.getParameter("field12")),connection).get())
                    .discount(Double.parseDouble(req.getParameter("field13")))
                    .build();
            int success = new PromotionDao().update(promotion,connection);
            List<Promotion> promotionList = new PromotionDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allPromotions", promotionList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/promotions.jsp").forward(req, resp);
        }
    }
}
