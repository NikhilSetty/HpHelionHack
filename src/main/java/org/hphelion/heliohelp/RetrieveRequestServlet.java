package org.hphelion.heliohelp;

import org.hphelion.heliohelp.Handlers.RequestHandler;
import org.hphelion.heliohelp.Handlers.ResponseHandler;
import org.hphelion.heliohelp.Handlers.UserHandler;
import org.hphelion.heliohelp.Interfaces.IRequestHandler;
import org.hphelion.heliohelp.Interfaces.IResponseHandler;
import org.hphelion.heliohelp.Interfaces.IUserHandler;
import org.hphelion.heliohelp.Model.Request;
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
public class RetrieveRequestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    IResponseHandler responseHandler = new ResponseHandler();
    IRequestHandler requestHandler = new RequestHandler();
    IUserHandler userHandler = new UserHandler();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Read the UserId param from Query String.
        String userId=request.getParameter("UserId");
        PrintWriter writer =response.getWriter();
        //Iterate over AssignedRequests and get each request from Database.
        User user = userHandler.getUser(Integer.parseInt(userId));
        String[] reqIds= user.RequestsAssigned.split(",");
        int count=reqIds.length,i=1;
        List<Request> requests=new ArrayList<Request>();

        while (i>count)
        {
            Request req=requestHandler.RetrieveRequest(Integer.parseInt(reqIds[i-1]));
            requests.add(req);
            i++;
        }
        //Append to the JSON Object.
        StringBuffer sb=new StringBuffer();
        sb.append("{\"UserId\":" + userId + ",\"Requests\":[");
        for(Iterator<Request> j = requests.iterator(); j.hasNext(); ) {
            Request item = j.next();
            User user1=userHandler.getUser(item.UserId);
            if(j.hasNext())
                sb.append("{\"RequestUserName\":\"" + user1.UserName + "\",\"RequesteUserId\":" + user1.Id + ",\"RequestMessage\":\"" + item.RequestMessage + "\",\"RequestedTime\":\"" +
                        item.TimeGenerated + "\",\"RequestId\":\"" + item.Id + "\",");
            else
                sb.append("{\"RequestUserName\":\"" + user1.UserName + "\",\"RequesteUserId\":" + user1.Id + ",\"RequestMessage\":\"" + item.RequestMessage + "\",\"RequestedTime\":\"" +
                        item.TimeGenerated + "\",\"RequestId\":\"" + item.Id + "\"");

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