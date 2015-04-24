package org.hphelion.heliohelp;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hphelion.heliohelp.Handlers.RequestHandler;
import org.hphelion.heliohelp.Handlers.UserHandler;
import org.hphelion.heliohelp.Interfaces.IRequestHandler;
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
    IRequestHandler requestHandler = new RequestHandler();
    GCM gcm=new GCM();
    int RequestType=3;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer jb = new StringBuffer();
        String line = null;
        JSONObject jsonObject=null;
        PrintWriter writer =response.getWriter();
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
        List<User> userIds=null;
        //If isCurrent, get Users to assign the request to based on Lat and Long given in the request object
        //Else, retrieve the registered location of the User who generated the request and get the Users to assign.
        if(req.IsCurrent)
        {
            userIds=userHandler.GetUsers(req.CurrentLatitude, req.CurrentLongitude);
        }
        else{
            User user= userHandler.getUser(req.UserId);
            System.out.println(user.Latitude1 + "," + user.Longitude1);
             userIds=userHandler.GetUsers(user.Latitude1, user.Longitude1);
        }
        StringBuffer sb = new StringBuffer();
        System.out.println("Log : " + userIds.size());
        //Set AssignedUsers property to the CSV of Assigned Users IDs
        for(Iterator<User> i = userIds.iterator(); i.hasNext(); ) {
            int item = i.next().Id;
            if(i.hasNext())
                sb.append(""+item+",");
            else
                sb.append(""+item);

        }
        String users=sb.toString();
        System.out.println(users);
        req.AssignedUsers=users;
        System.out.println(sb.toString() + "g1" + users);
        //Add Request record in the database
        int reqId=requestHandler.AddRequest(req);
        User user=userHandler.getUser(req.UserId);
        if(reqId>0){
            //!Send Push Notification to all these Users.

            String value = "{\"data\": {\"message\":" + '"' + req.RequestMessage + '"' + ",\"userName\":" + '"' +
                    user.UserName + '"' + ",\"requestId\":" + user.Id + ",\"Type\":"  + RequestType +

                    "},\"registration_ids\":[";


            for(Iterator<User> i = userIds.iterator(); i.hasNext(); ) {
                User item = i.next();
                if(i.hasNext())
                    value += "\"" + item.RegistrationId + "\",";
                else
                    value += "\"" + item.RegistrationId + "\"";

            }

            value +="]}";
            System.out.println(value);
            boolean successful= gcm.SendGCM(value);

            //Update the RequestsAssigned field for all users to whom this request has been assigned and Update the Database.
            StringBuffer sb1 = new StringBuffer();
            for(Iterator<User> i = userIds.iterator(); i.hasNext(); ) {
                User item = i.next();
                sb1 = new StringBuffer();

                if(item.RequestsAssigned==null)
                {
                    System.out.println("Requests assgined is null!! for user " + item.UserName);
                    System.out.println(sb1);
                    sb1.append(""+reqId);
                    System.out.println("Sb1 after append : " + sb1 );
                    String reqAssigned = sb1.toString();
                    System.out.println("Req assigned : " + reqAssigned );
                    item.RequestsAssigned=reqAssigned;
                    int test=userHandler.UpdateUser(item);
                    if(test==1)
                        System.out.println(" null : Success");
                    else
                        System.out.println("F");
                }
                else{
                    System.out.println("Requests assgined is not null!! for user " + item.UserName);
                    System.out.println(sb1);
                    sb1.append(item.RequestsAssigned);
                    System.out.println("Sb1 after request assigned append : " + sb1);
                    sb1.append("," + reqId);
                    System.out.println("sb1 append: " + sb1 );
                    String reqAssigned=sb1.toString();
                    System.out.println("Req assigned : " + reqAssigned );
                    item.RequestsAssigned=reqAssigned;
                    int test=userHandler.UpdateUser(item);
                    if(test==1)
                        System.out.println("Success");
                    else
                        System.out.println("F");
                }


            }

        }
        if(reqId>0)
            writer.print(""+reqId);
        else
            writer.print("Posted failed");
        //Return Request id
    }
}