package org.example.simplecrud.servlets.tables;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.simplecrud.dao.SupplierDao;
import org.example.simplecrud.dto.SupplierDto;
import org.example.simplecrud.entity.Supplier;
import org.example.simplecrud.service.SupplierService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/suppliers")
public class SupplierServlet extends HttpServlet {
    private String id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Supplier> supplierList = new SupplierDao().findAll().stream().sorted().collect(Collectors.toList());
        req.setAttribute("allSuppliers", supplierList);
        req.getRequestDispatcher("/WEB-INF/suppliers.jsp").forward(req, resp);
        req.setAttribute("check", 0);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("Save".equals(action)) {
            SupplierDto user = SupplierDto.builder()
                    .id(req.getParameter("field1"))
                    .companyName(req.getParameter("field2"))
                    .contactPerson(req.getParameter("field3"))
                    .address(req.getParameter("field4"))
                    .email(req.getParameter("field5"))
                    .build();
            SupplierService.save(user);
            req.setAttribute("allSuppliers", SupplierService.takeList());
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/suppliers.jsp").forward(req, resp);
        }
        else if ("Delete".equals(action)) {
            String idsString = req.getParameter("dfield");
            SupplierService.delete(idsString);
            req.setAttribute("allSuppliers", SupplierService.takeList());
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/suppliers.jsp").forward(req, resp);
        }
        else if ("Update".equals(action)) {
            id = req.getParameter("ufield");
            req.setAttribute("Supplier", SupplierService.findSupplier(id));
            req.setAttribute("allSuppliers", SupplierService.takeList());
            req.setAttribute("check", 1);
            req.getRequestDispatcher("/WEB-INF/suppliers.jsp").forward(req, resp);
        }
        else if ("Submit".equals(action)) {
            SupplierDto supplier = SupplierDto.builder()
                    .id(id)
                    .companyName(req.getParameter("field10"))
                    .contactPerson(req.getParameter("field11"))
                    .address(req.getParameter("field12"))
                    .email(req.getParameter("field13"))
                    .build();
            SupplierService.updateSupplier(supplier);
            req.setAttribute("allSuppliers", SupplierService.takeList());
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/suppliers.jsp").forward(req, resp);
        }
    }
}
