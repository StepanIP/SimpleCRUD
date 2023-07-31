package org.example.simplecrud.servlets.tables;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.simplecrud.dao.*;
import org.example.simplecrud.entity.Payment;
import org.example.simplecrud.entity.enums.PeymentMethod;
import org.example.simplecrud.utill.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/payments")
public class PaymentServlet extends HttpServlet {

    private int id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Payment> paymentList = new PaymentDao().findAll().stream().sorted().collect(Collectors.toList());
        req.setAttribute("allPayments", paymentList);
        req.getRequestDispatcher("/WEB-INF/payments.jsp").forward(req, resp);
        req.setAttribute("check", 0);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = ConnectionManager.get();
        String action = req.getParameter("action");
        if ("Save".equals(action)) {
            Payment user = Payment.builder()
                    .id(Integer.parseInt(req.getParameter("field1")))
                    .peymentMethod(PeymentMethod.valueOf(req.getParameter("field2")))
                    .amount(Double.parseDouble(req.getParameter("field3")))
                    .order(new OrderDao().findById(Integer.parseInt(req.getParameter("field4")),connection).get())
                    .build();
            new PaymentDao().save(user, connection);
            List<Payment> paymentList = new PaymentDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allPayments", paymentList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/payments.jsp").forward(req, resp);
        }
        else if ("Delete".equals(action)) {
            String idsString = req.getParameter("dfield");
            boolean paymentDao = new PaymentDao().delete(Integer.parseInt(idsString),connection);
            List<Payment> paymentList = new PaymentDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allPayments", paymentList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/payments.jsp").forward(req, resp);
        }
        else if ("Update".equals(action)) {
            String uString = req.getParameter("ufield");
            id=Integer.parseInt(uString);
            Payment payment = new PaymentDao().findById(id,connection).get();
            List<Payment> paymentList = new PaymentDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("Payment", payment);
            req.setAttribute("allPayments", paymentList);
            req.setAttribute("check", 1);
            req.getRequestDispatcher("/WEB-INF/payments.jsp").forward(req, resp);
        }
        else if ("Submit".equals(action)) {
            Payment user = Payment.builder()
                    .id(id)
                    .peymentMethod(PeymentMethod.valueOf(req.getParameter("field10")))
                    .amount(Double.parseDouble(req.getParameter("field11")))
                    .order(new OrderDao().findById(Integer.parseInt(req.getParameter("field12")),connection).get()).build();
            int success = new PaymentDao().update(user,connection);
            List<Payment> paymentList = new PaymentDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allPayments", paymentList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/payments.jsp").forward(req, resp);
        }
    }
}
