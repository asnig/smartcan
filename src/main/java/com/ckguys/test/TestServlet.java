package com.ckguys.test;

import com.ckguys.tools.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author XYQ
 */
@WebServlet(name = "TestServlet", urlPatterns = "/TestServlet")
public class TestServlet extends BaseServlet {

    public String test(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloWorld!");
        return "f:index";
    }
}
