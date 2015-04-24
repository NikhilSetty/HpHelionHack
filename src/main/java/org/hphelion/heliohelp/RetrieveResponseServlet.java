package org.hphelion.heliohelp;

import org.hphelion.heliohelp.Handlers.RequestHandler;
import org.hphelion.heliohelp.Handlers.ResponseHandler;
import org.hphelion.heliohelp.Handlers.UserHandler;
import org.hphelion.heliohelp.Interfaces.IRequestHandler;
import org.hphelion.heliohelp.Interfaces.IResponseHandler;
import org.hphelion.heliohelp.Interfaces.IUserHandler;
import org.hphelion.heliohelp.Model.Request;
import org.hphelion.heliohelp.Model.Response;
import org.hphelion.heliohelp.Model.User;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by PGonagur on 4/22/2015.
 */
public class RetrieveResponseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    IResponseHandler responseHandler = new ResponseHandler();
    IRequestHandler requestHandler = new RequestHandler();
    IUserHandler userHandler = new UserHandler();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Read the RequestId param from Query String.
        String requestId=request.getParameter("RequestId");
        PrintWriter writer =response.getWriter();
        //Get all responses from Database where response.RequestID = RequestId.
        List<Response> responses = new ArrayList<Response>();
        responses=responseHandler.RetrieveResponse(Integer.parseInt(requestId));
        //Make a JSON Object by iterating over response objects.

        StringBuffer sb=new StringBuffer();
        sb.append("{\"RequestId\":" + requestId + ",\"Responses\":[");
        for(Iterator<Response> j = responses.iterator(); j.hasNext(); ) {
            Response item = j.next();
            User user1=userHandler.getUser(item.UserId);
            if(j.hasNext())
                sb.append("{\"ResponseUserName\":\"" + user1.UserName + "\",\"ResponseUserId\":" + user1.Id + ",\"ResponseString\":\"" + item.Message + "\",\"ResponseTime\":\"" +
                        item.TimeGenerated + "\",\"ResponseId\":\"" + item.Id + "\"},");
            else
                sb.append("{\"ResponseUserName\":\"" + user1.UserName + "\",\"ResponseUserId\":" + user1.Id + ",\"ResponseString\":\"" + item.Message + "\",\"ResponseTime\":\"" +
                        item.TimeGenerated + "\",\"ResponseId\":\"" + item.Id + "\"}");

        }

        //Return the JSON Object.
        sb.append("]}");
        String jsonString = sb.toString();
        writer.print(jsonString);

        /*response.setContentType("text/plain");
        response.setStatus(200);
        PrintWriter writer = response.getWriter();
        writer.println("I am adding user");
        writer.close();*/
    }
}