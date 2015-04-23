package org.hphelion.heliohelp;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hphelion.heliohelp.Handlers.UserHandler;
import org.hphelion.heliohelp.Interfaces.IUserHandler;
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

/**
 * Created by NJere on 4/23/2015.
 */
public class UpdateRegId extends HttpServlet {
    IUserHandler userHandler = new UserHandler();
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
        User user = new User();
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            user =  mapper.readValue(jsonObject.toString(),User.class);
            //writer.print(user.EmailId);

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
       // boolean emailExists=userHandler.EmailAlreadyExists(user.EmailId);
       // if(!emailExists)
      //  {
            //Add User if it does not exist
            userId =userHandler.UpdateUserReg(user.Id, user.RegistrationId);
        //}


        writer.print(""+userId);
        //writer.print("INSERT INTO Users VALUES ("+user.UserName+","+user.EmailId+","+user.Password+","+user.Pincode1+","+user.Address1+","+user.Latitude1+","  +  user.Longitude1+")");
        //Return ID (existing or new
    }
}
