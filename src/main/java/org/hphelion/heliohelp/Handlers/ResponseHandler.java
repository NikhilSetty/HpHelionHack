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
            String sql="INSERT INTO Responses VALUES ('"+response.Message+"','"+response.RequestId+"',"+response.UserId+",'"+response.TimeGenerated+"')";
            statement.executeUpdate(sql);

            String sql2="Select * from Responses order by Id desc";
            rs=statement.executeQuery(sql2);
            if(rs.next())
            {
                return rs.getInt("Id");
            }
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
            return 0;
        } finally{
            //finally block used to close resources

            try{
                if(statement!=null)
                    statement.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
            }//end finally try
        } //end try
        return 0;
    }

    public List<Response> RetrieveResponse(int requestId){
        Connection conn=null;
        Statement statement=null;
        ResultSet rs=null;
        List<Response> responses=new ArrayList<Response>();
        try {
            conn = dbTest.getConnection();
            statement = conn.createStatement();
            String sql="Select * from Response where RequestId="+requestId;
            rs = statement.executeQuery(sql);
            while(rs.next())
            {
                Response response=new Response();
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
        } finally{
            //finally block used to close resources
            try{
                if(rs!=null)
                    rs.close();
            }catch(SQLException se2){
            }
            try{
                if(statement!=null)
                    statement.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
            }//end finally try
        } //end try
        return null;
    }
}
