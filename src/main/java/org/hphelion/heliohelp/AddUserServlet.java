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
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by NiRavishankar on 4/22/2015.
 */
public class AddUserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /*protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setStatus(200);
        PrintWriter writer = response.getWriter();
        writer.println("I am adding user");
        writer.close();
    }*/
    IUserHandler userHandler= new UserHandler();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer jb = new StringBuffer();
        String line = null;
        JSONObject jsonObject=null;
        PrintWriter writer =response.getWriter();
        int userId=0;
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

        //Map the JSON Object to User Object.
        User user = null;
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            user =  mapper.readValue(jsonObject.toString(),User.class);
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
        //System.out.println(employee);

        //Check if User Already exists in Database using email id.
        boolean emailExists=userHandler.EmailAlreadyExists(user.EmailId);
        if(!emailExists)
        {
            //Add User if it does not exist
            userId =userHandler.AddUser(user);
        }

        else{

            writer.print("ERROR-Email Exists");
        }

            writer.print(""+userId);
        //Return ID (existing or new
    }
}
