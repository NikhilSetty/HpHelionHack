package org.hphelion.heliohelp.Handlers;

import org.hphelion.heliohelp.DbAccess;
import org.hphelion.heliohelp.Interfaces.IDbAccess;
import org.hphelion.heliohelp.Interfaces.IResponseHandler;
import org.hphelion.heliohelp.Model.Request;
import org.hphelion.heliohelp.Model.Response;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NJere on 4/23/2015.
 */
public class ResponseHandler implements IResponseHandler {
    IDbAccess dbTest=null;

    public ResponseHandler(){
        dbTest=new DbAccess();
    }

    public int AddResponse(Response response){
        Connection conn=null;
        Statement statement=null;
        ResultSet rs=null;
        try{
            conn = dbTest.getConnection();
            statement=conn.createStatement();
            String sql="INSERT INTO Responses VALUES ("+response.Message+","+response.RequestId+","+response.UserId+","+response.TimeGenerated+")";
            rs = statement.executeQuery(sql);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return 1;
    }

    public List<Response> RetrieveResponse(int requestId){
        Connection conn=null;
        Statement statement=null;
        ResultSet rs=null;
        Response response=new Response();
        List<Response> responses=new ArrayList<Response>();
        try {
            conn = dbTest.getConnection();
            statement = conn.createStatement();
            String sql="Select * from Response where RequestId="+requestId;
            rs = statement.executeQuery(sql);
            while(rs.next())
            {
                response.Id = rs.getInt("Id");
                response.Message=rs.getString("ResponseMessage");
                response.RequestId=rs.getInt("RequestId");
                response.UserId=rs.getInt("UserId");
                response.TimeGenerated=rs.getString("TimeGenerated");
                responses.add(response);
            }
            return responses;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
