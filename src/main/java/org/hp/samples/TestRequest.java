package org.hp.samples;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by NiRavishankar on 4/22/2015.
 */
public class TestRequest extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setStatus(200);
        PrintWriter writer = response.getWriter();
        writer.println("Hello World");
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setStatus(200);
        PrintWriter writer = response.getWriter();
        writer.println("POST : " + req.getInputStream());
        writer.println("Hello World");
        writer.close();
    }
}
