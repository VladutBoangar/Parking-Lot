package com.parkinglot.parkinglot.servlets;

import com.parkinglot.parkinglot.common.CarDto;
import com.parkinglot.parkinglot.common.UserDto;
import com.parkinglot.parkinglot.ejb.CarsBean;
import com.parkinglot.parkinglot.ejb.UserBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.nio.charset.CharsetDecoder;
import java.util.List;

@WebServlet(name = "Users", value = "/Users")
public class Users extends HttpServlet {

    @Inject
    UserBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserDto> users = usersBean.findAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request,response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
    }
}