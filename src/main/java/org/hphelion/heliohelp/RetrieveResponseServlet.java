package org.hphelion.heliohelp;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by PGonagur on 4/22/2015.
 */
public class RetrieveResponseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Read the RequestId param from Query String.

        //Get all responses from Database where response.RequestID = RequestId.

        //Make a JSON Object by iterating over response objects.

        //Return the JSON Object.

        /*response.setContentType("text/plain");
        response.setStatus(200);
        PrintWriter writer = response.getWriter();
        writer.println("I am adding user");
        writer.close();*/
    }
}