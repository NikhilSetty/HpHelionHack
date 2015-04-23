package org.hphelion.heliohelp.Handlers;

import org.hphelion.heliohelp.DbAccess;
import org.hphelion.heliohelp.Interfaces.IDbAccess;
import org.hphelion.heliohelp.Interfaces.IRequestHandler;
import org.hphelion.heliohelp.Model.Request;


import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * Created by NJere on 4/22/2015.
 */
public class RequestHandler implements IRequestHandler{
    IDbAccess dbTest=null;
   public  RequestHandler()
    {
        dbTest = new DbAccess();
    }

    public int AddRequest(Request request) {
        Connection conn=null;
        Statement statement=null;
        ResultSet rs=null;
        try{
             conn = dbTest.getConnection();
             statement=conn.createStatement();
            String sql="INSERT INTO Requests VALUES ("+request.RequestMessage+","+request.IsCurrent+","+request.CurrentLatitude+"," +
                    request.CurrentLongitude+","+request.UserId+")";
             rs = statement.executeQuery(sql);
            boolean isInserted=rs.rowInserted();
            if(isInserted)
            {
                String sql2="Select * from Requests orderby Id";
                rs=statement.executeQuery(sql2);
                if(rs.next())
                {
                    rs.close();
                    statement.close();
                    conn.close();
                    return rs.getInt("Id");
                }
                rs.close();
                statement.close();
                conn.close();
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return 0;
        }

        return 0;
    }

    public void UpdateRequest(Request request) throws SQLException
    {
        Connection conn=null;
        Statement statement=null;
        ResultSet rs=null;
        Request req=new Request();
        try{
            conn = dbTest.getConnection();
            statement=conn.createStatement();
            String sql="SELECT * FROM Requests WHERE Id="+request.Id;
            rs = statement.executeQuery(sql);
            if(rs.next())
            {
                req.Id=rs.getInt("Id");
                req.UserId=rs.getInt("UserId");
                req.RequestMessage=rs.getString("RequestMessage");
                req.CurrentLongitude=rs.getFloat("CurrentLongitude");
                req.CurrentLatitude=rs.getFloat("CurrentLatitude");
                req.IsCurrent=rs.getBoolean("IsCurrent");
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();

        }
        finally {
            rs.close();
            statement.close();
            conn.close();
        }

    }

    public Request RetrieveRequest(int requestId)
    {

        Connection conn=null;
        Statement statement=null;
        ResultSet rs=null;
        Request req=new Request();
        try {
            conn = dbTest.getConnection();
            statement = conn.createStatement();
            String sql = "SELECT * FROM Requests WHERE Id="+requestId;
            rs = statement.executeQuery(sql);
            if(rs.next())
            {
                req.Id=rs.getInt("Id");
                req.UserId=rs.getInt("UserId");
                req.RequestMessage=rs.getString("RequestMessage");
                req.CurrentLongitude=rs.getFloat("CurrentLongitude");
                req.CurrentLatitude=rs.getFloat("CurrentLatitude");
                req.IsCurrent=rs.getBoolean("IsCurrent");
            }
            return req;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
