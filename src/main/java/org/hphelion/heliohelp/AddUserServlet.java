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
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by NiRavishankar on 4/22/2015.
 */
public class AddUserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String STORE_NAME = "HelioHelp";
    private static final String API_KEY= "01bf3098-7549-46ad-8864-322226763658";
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
            //Add User if it does not exist
            userId =userHandler.AddUser(user);
        if(userId>0){
            try{
                AddUserToStore(user);
            }catch (Exception e){

            }

        }

      /*  else{

            writer.print("ERROR-Email Exists");
        }

            writer.print(""+userId);
        else
            writer.print("ERROR");*/
            //writer.print("INSERT INTO Users VALUES ("+user.UserName+","+user.EmailId+","+user.Password+","+user.Pincode1+","+user.Address1+","+user.Latitude1+","  +  user.Longitude1+")");
        //Return ID (existing or new
    }

    private boolean AddUserToStore(User user) throws Exception {

        String url = "https://api.idolondemand.com/1/api/sync/adduser/v1?store="+STORE_NAME+"&email="+user.EmailId+"&password="+user.Password+"&apikey="+API_KEY;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }try{
            JSONObject jsonObject = new JSONObject(response);
            boolean success = jsonObject.getBoolean("success");
            return success;
        }catch (Exception e){
            return false;
        }

    }
}
