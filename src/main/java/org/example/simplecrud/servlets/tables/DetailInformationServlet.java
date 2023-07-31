package org.example.simplecrud.servlets.tables;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.simplecrud.dao.CartDao;
import org.example.simplecrud.dao.DetailInformationDao;
import org.example.simplecrud.entity.DetailInformation;
import org.example.simplecrud.entity.enums.Country;
import org.example.simplecrud.utill.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/detail_informations")
public class DetailInformationServlet extends HttpServlet {
    private int id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<DetailInformation> detailInformationList = new DetailInformationDao().findAll().stream().sorted().collect(Collectors.toList());
        req.setAttribute("allDT", detailInformationList);
        req.setAttribute("check", 0);
        req.getRequestDispatcher("/WEB-INF/detail_information.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = ConnectionManager.get();
        String action = req.getParameter("action");
        if ("Save".equals(action)) {
            DetailInformation detailInformation = DetailInformation.builder()
                    .id(Integer.parseInt(req.getParameter("field1")))
                    .age(Integer.parseInt(req.getParameter("field2")))
                    .country(Country.valueOf(req.getParameter("field3").replaceAll(" ","_").toUpperCase()))
                    .deliveryAddress(req.getParameter("field4"))
                    .cart(new CartDao().findById(Integer.parseInt(req.getParameter("field5")),connection).get())
                    .build();
            new DetailInformationDao().save(detailInformation, connection);
            List<DetailInformation> detailInformationList = new DetailInformationDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allDT", detailInformationList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/detail_information.jsp").forward(req, resp);
        }
        else if ("Delete".equals(action)) {
            String idsString = req.getParameter("dfield");
            boolean detailDao = new DetailInformationDao().delete(Integer.parseInt(idsString),connection);
            List<DetailInformation> detailInformationList = new DetailInformationDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allDT", detailInformationList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/detail_information.jsp").forward(req, resp);
        }
        else if ("Update".equals(action)) {
            String uString = req.getParameter("ufield");
            id=Integer.parseInt(uString);
            DetailInformation detailInformation = new DetailInformationDao().findById(id,connection).get();
            List<DetailInformation> detailInformationList = new DetailInformationDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("DT", detailInformation);
            req.setAttribute("allDT", detailInformationList);
            req.setAttribute("check", 1);
            req.getRequestDispatcher("/WEB-INF/detail_information.jsp").forward(req, resp);
        }
        else if ("Submit".equals(action)) {
            DetailInformation detailInformation = DetailInformation.builder()
                    .id(id)
                    .age(Integer.parseInt(req.getParameter("field10")))
                    .country(Country.valueOf(req.getParameter("field11").replaceAll(" ","_").toUpperCase()))
                    .deliveryAddress(req.getParameter("field12"))
                    .cart(new CartDao().findById(Integer.parseInt(req.getParameter("field13")),connection).get())
                    .build();
            int success = new DetailInformationDao().update(detailInformation,connection);
            List<DetailInformation> detailInformationList = new DetailInformationDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allDT", detailInformationList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/detail_information.jsp").forward(req, resp);
        }
    }
}
