package org.hp.samples;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.String;
import java.sql.*;
import java.text.ParseException;

/**
 * Created by NiRavishankar on 4/22/2015.
 */
public class DbTest extends HttpServlet{
    private static final long serialVersionUID = 1L;

    Connection conn = null;
    Statement stmt = null;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        // JDBC driver name and database URL
        final String JDBC_DRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        final String DB_URL="jdbc:sqlserver://tddowsht2o.database.windows.net;databaseName=Teach-Mate-DB";

        //jdbc:microsoft:sqlserver://
        //  Database credentials
        final String USER = "anuj";
        final String PASS = "P@ssw0rd123";

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try{

            // Register JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Open a connection
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();

            // Execute SQL query
            String sql;
            sql = "select username from Users";
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                String id  = rs.getString("username");

                //Display values
                out.println("Name: " + id + "<br>");
            }
            out.println("</body></html>");

            // Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
            out.println(se);
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
            out.println(e);
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
                out.println(se.getMessage());
            }//end finally try
        } //end try
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        try {
            JSONObject jsonObject = new JSONObject(jb.toString());
        } catch (Exception e) {
            // crash and burn
            throw new IOException("Error parsing JSON request string");
        }
        /*response.setContentType("text/plain");
        response.setStatus(200);
        PrintWriter writer = response.getWriter();
        writer.println("I am adding user");
        writer.close();*/
    }
}
