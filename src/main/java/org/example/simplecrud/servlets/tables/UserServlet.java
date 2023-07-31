package org.example.simplecrud.servlets.tables;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.simplecrud.dao.DetailInformationDao;
import org.example.simplecrud.dao.UserDao;
import org.example.simplecrud.entity.User;
import org.example.simplecrud.utill.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private int id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> userList = new UserDao().findAll().stream().sorted().collect(Collectors.toList());
        req.setAttribute("allUsers", userList);
        req.getRequestDispatcher("/WEB-INF/users.jsp").forward(req, resp);
        req.setAttribute("check", 0);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = ConnectionManager.get();
        String action = req.getParameter("action");
        if ("Save".equals(action)) {
            User user = User.builder()
                    .id(Integer.parseInt(req.getParameter("field1")))
                    .email(req.getParameter("field2"))
                    .phoneNumber(req.getParameter("field3"))
                    .password(Objects.hash(req.getParameter("field4")))
                    .firstName(req.getParameter("field5"))
                    .surname(req.getParameter("field6"))
                    .detailInformation(new DetailInformationDao().findById(Integer.parseInt(req.getParameter("field7")), connection).get())
                    .build();
            new UserDao().save(user, connection);
            List<User> userList = new UserDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allUsers", userList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/users.jsp").forward(req, resp);
        }
        else if ("Delete".equals(action)) {
            String idsString = req.getParameter("dfield");
            boolean userDao = new UserDao().delete(Integer.parseInt(idsString),connection);
            List<User> userList = new UserDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allUsers", userList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/users.jsp").forward(req, resp);
        }
        else if ("Update".equals(action)) {
            String uString = req.getParameter("ufield");
            id=Integer.parseInt(uString);
            User user = new UserDao().findById(id,connection).get();
            List<User> userList = new UserDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("User", user);
            req.setAttribute("allUsers", userList);
            req.setAttribute("check", 1);
            req.getRequestDispatcher("/WEB-INF/users.jsp").forward(req, resp);
        }
        else if ("Submit".equals(action)) {
            User user = User.builder()
                    .id(id)
                    .email(req.getParameter("field10"))
                    .phoneNumber(req.getParameter("field11"))
                    .password(Objects.hash(req.getParameter("field12")))
                    .firstName(req.getParameter("field13"))
                    .surname(req.getParameter("field14"))
                    .detailInformation(new DetailInformationDao().findById(Integer.parseInt(req.getParameter("field15")), connection).get())
                    .build();
            int success = new UserDao().update(user,connection);
            List<User> userList = new UserDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allUsers", userList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/users.jsp").forward(req, resp);
        }
    }
}
