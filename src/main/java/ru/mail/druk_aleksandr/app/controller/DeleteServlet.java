package ru.mail.druk_aleksandr.app.controller;

import ru.mail.druk_aleksandr.app.service.UserService;
import ru.mail.druk_aleksandr.app.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        userService.deleteUserById(Integer.parseInt(userId));
        resp.sendRedirect(req.getContextPath() + "/users");
    }
}
