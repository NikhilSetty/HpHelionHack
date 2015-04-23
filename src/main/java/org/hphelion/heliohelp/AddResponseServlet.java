package org.hphelion.heliohelp;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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

/**
 * Created by PGonagur on 4/22/2015.
 */
public class AddResponseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setStatus(200);
        PrintWriter writer = response.getWriter();
        writer.println("I am adding user");
        writer.close();
    }
    IResponseHandler responseHandler = new ResponseHandler();
    IRequestHandler requestHandler = new RequestHandler();
    IUserHandler userHandler = new UserHandler();
    GCM gcm=new GCM();
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

        //Map the JSON Object to Response Object.
        Response response1 = null;
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            response1 =  mapper.readValue(jsonObject.toString(), Response.class);
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

        //Add Response Object to the Database.
        int responseId=responseHandler.AddResponse(response1);
        if(responseId>0) {
            //Retrieve the Request Object from database.
            Request req = requestHandler.RetrieveRequest(response1.RequestId);
            //Retrieve the User Object of User who generated the Request.
            User user = userHandler.getUser(req.UserId);
            //!Send Push Notification to the User.
            String value = "{\"data\": {\"ResponseUserId\":" + '"'
                    + response1.UserId + '"' + ",\"ResponseMessage\":" + '"' + response1.Message + '"' + ",\"ResponseId\":" + response1.Id
                    + ",\"ResponseUserName\":" + '"' + responseModel.UserName  + ",\"Type\":" + ResponseType + ",\"RequestId\":" + '"' + response1.RequestId + '"'
                    + "},\"registration_ids\":[\"" + user.RegistrationId + "\"]}";
            boolean successful=gcm.SendGCM(value);
        }
        //Return Response id
        writer.print(""+responseId);

    }
}