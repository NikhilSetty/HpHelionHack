package org.hphelion.heliohelp;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hphelion.heliohelp.Handlers.UserHandler;
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
import java.util.Iterator;
import java.util.List;

/**
 * Created by PGonagur on 4/22/2015.
 */
public class AddRequestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    IUserHandler userHandler= new UserHandler();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer jb = new StringBuffer();
        String line = null;
        JSONObject jsonObject=null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        try {
             jsonObject = new JSONObject(jb.toString());
        } catch (JSONException e) {
            // crash and burn
            throw new IOException("Error parsing JSON request string");
        }

        //Map the JSON Object to Request Object.
    Request req = null;
    ObjectMapper mapper = new ObjectMapper();
    try
    {
        req =  mapper.readValue(jsonObject.toString(), Request.class);
    } catch (JsonGenerationException e)
    {
        e.printStackTrace();
    } catch (JsonMappingException e)
    {
        e.printStackTrace();
    } catch (IOException e)
    {
        e.printStackTrace();
    }
    // System.out.println(employee);
        List<Integer> userIds=null;
        //If isCurrent, get Users to assign the request to based on Lat and Long given in the request object
        //Else, retrieve the registered location of the User who generated the request and get the Users to assign.
        if(req.IsCurrent)
        {
            //userIds=userHandler.getUsers(req.CurrentLatitude, req.CurrentLongitude);
        }
        else{
            //User user= userHandler.getUser(req.UserId);
            // userIds=userHandler.getUsers(user.Latitude, user.Longitude);
        }

        //Set AssignedUsers property to the CSV of Assigned Users IDs
        for(Iterator<Integer> i = userIds.iterator(); i.hasNext(); ) {
            int item = i.next();

        }

        //Add Request record in the database
        //!Send Push Notification to all these Users.

        //Update the RequestsAssigned field for all users to whom this request has been assigned and Update the Database.

        //Return Request id
    }
}