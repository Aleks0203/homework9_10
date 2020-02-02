package ru.mail.druk_aleksandr.app.controller;

import ru.mail.druk_aleksandr.app.service.UserService;
import ru.mail.druk_aleksandr.app.service.impl.UserServiceImpl;
import ru.mail.druk_aleksandr.app.service.model.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddServlet extends HttpServlet {
    private static final String PAGES = "/WEB-INF/pages";
    private static final int AGE_MIN = 1;
    private static final int AGE_MAX = 100;
    private static final String PATERN_FORMAT_FOR_NAME = "[a-zA-Z]{1,40}";
    private static final String PATERN_FORMAT_FOR_PASSWORD = "[0-9]{4,40}";
    private static final String PATERN_FORMAT_FOR_TELEPHONE = "^(\\+375|80)(29|25|44|33)(\\d{3})(\\d{2})(\\d{2})$";
    private static final String PATERN_FORMAT_FOR_ADDRESS = "^(city)\\s(\\D{1,33})\\s(street)\\s(\\D{1,44})$";
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(PAGES + "/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = getUserDTO(req, resp);
        if (userValidation(userDTO)) {
            userService.addUser(userDTO);
            resp.sendRedirect(req.getContextPath() + "/users");
        } else {
            getServletContext().getRequestDispatcher(PAGES + "/notfound.jsp").forward(req, resp);
        }
    }

    private UserDTO getUserDTO(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(req.getParameter("username"));
            userDTO.setPassword(req.getParameter("password"));
            String ageString = req.getParameter("age");
            userDTO.setAge(Integer.parseInt(ageString));
            String activeString = req.getParameter("is_active");
            userDTO.setActive(Boolean.parseBoolean(activeString));
            userDTO.setTelephone(req.getParameter("telephone"));
            userDTO.setAddress(req.getParameter("address"));
            return userDTO;
        } catch (Exception ex) {
            getServletContext().getRequestDispatcher(PAGES + "/notfound.jsp").forward(req, resp);
        }
        return null;
    }

    static boolean userValidation(UserDTO userDTO) {
        if (userDTO.getUsername().matches(PATERN_FORMAT_FOR_NAME) &&
                userDTO.getPassword().matches(PATERN_FORMAT_FOR_PASSWORD) &&
                userDTO.getAge() > AGE_MIN && userDTO.getAge() < AGE_MAX &&
                userDTO.getTelephone().matches(PATERN_FORMAT_FOR_TELEPHONE) &&
                userDTO.getAddress().matches(PATERN_FORMAT_FOR_ADDRESS)
        ) {
            return true;
        } else {
            return false;
        }
    }
}
