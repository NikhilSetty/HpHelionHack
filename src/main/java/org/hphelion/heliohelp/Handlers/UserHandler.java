package org.hphelion.heliohelp.Handlers;

import org.hphelion.heliohelp.DbAccess;
import org.hphelion.heliohelp.Interfaces.IDbAccess;
import org.hphelion.heliohelp.Interfaces.IUserHandler;
import org.hphelion.heliohelp.Model.User;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by NJere on 4/23/2015.
 */
public class UserHandler implements IUserHandler {
    IDbAccess dbTest=null;
    public  UserHandler()
    {
        dbTest = new DbAccess();
    }
    public boolean EmailAlreadyExists(String email)
    {
        Connection conn=null;
        Statement statement=null;
        ResultSet rs=null;
        try{
            conn = dbTest.getConnection();
            statement=conn.createStatement();
            String sql="SELECT * FROM Users WHERE Email="+email;
            rs = statement.executeQuery(sql);

                if(rs.next())
                {
                    rs.close();
                    statement.close();
                    conn.close();
                    return true;
                }
            rs.close();
            statement.close();
            conn.close();
            return false;

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();

        }
        return true;
    }

    public int AddUser(User user)
    {
        String out = new String();
        Connection conn=null;
        Statement statement=null;
        ResultSet rs=null;
        try{
            conn = dbTest.getConnection();
            statement=conn.createStatement();
            String sql="INSERT INTO Users VALUES ("+user.UserName+","+user.EmailId+","+user.Password+","+user.Pincode1+","+user.Address1+","+user.Latitude1+"," +
                    user.Longitude1+")";
            rs = statement.executeQuery(sql);
            boolean isInserted=rs.rowInserted();
            if(isInserted)
            {
                String sql2="Select Id from Users orderby Id desc";
                rs=statement.executeQuery(sql2);
                if(rs.next())
                {
                    int id = rs.getInt("Id");
                    return id;
                }
            }
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
            out += se + "\n";
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
            out += e + "\n";
        }finally{
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
                se.printStackTrace();
                out += se.getMessage() + "\n";
            }//end finally try
        } //end try
        return 0;
    }

    public int UpdateUserReg(int userId,String regId) throws SQLException {
        Connection conn=null;
        Statement statement=null;
        ResultSet rs=null;
        User user=new User();
        try {
            conn = dbTest.getConnection();
            statement = conn.createStatement();
            String sql = "UPDATE Users SET RegistrationId="+regId+"where Id="+userId;
            rs = statement.executeQuery(sql);
            rs.close();
            statement.close();
            conn.close();
            return 1;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        rs.close();
        statement.close();
        conn.close();
        return 0;
    }

    public User getUser(int userId)
    {
        Connection conn=null;
        Statement statement=null;
        ResultSet rs=null;
        User user=new User();
        try {
            conn = dbTest.getConnection();
            statement = conn.createStatement();
            String sql = "SELECT * FROM Users where Id="+userId;
            rs = statement.executeQuery(sql);
            if(rs.next()) {
                user.Id=rs.getInt("Id");
                user.Address1=rs.getString("Address");
                user.EmailId=rs.getString("Email");
                user.Latitude1=rs.getFloat("Latitude");
                user.Longitude1=rs.getFloat("Longitude");
                user.Password=rs.getString("Password");
                user.UserName=rs.getString("Name");
                user.Pincode1=rs.getString("Pinocde");
                user.RegistrationId=rs.getString("RegistrationId");
                return user;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
