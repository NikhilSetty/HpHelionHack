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
import java.util.ArrayList;
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
    public boolean EmailAlreadyExists(String email){
        Connection conn=null;
        Statement statement=null;
        ResultSet rs=null;
        try{
            conn = dbTest.getConnection();
            statement=conn.createStatement();
            String sql="SELECT * FROM Users WHERE Email="+email;
            rs = statement.executeQuery(sql);

                if(rs.first())
                {
                    return true;
                }
            return false;

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();

        }
        finally{
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

    public int UpdateUserReg(int userId,String regId){
        Connection conn=null;
        Statement statement=null;
        ResultSet rs=null;
        try {
            conn = dbTest.getConnection();
            statement = conn.createStatement();
            String sql = "UPDATE Users SET RegistrationId="+regId+"where Id="+userId;
            rs = statement.executeQuery(sql);
            return 1;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
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
        return 0;
    }

    public User getUser(int userId) {
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
                user.RequestsAssigned=rs.getString("RequestsAssigned");
                return user;
            }
        }
        catch (Exception e){
            e.printStackTrace();
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
            }//end finally try
        } //end try
        return null;
    }


    public List<User> GetUsers (Float latitude, Float longitude) throws SQLException {
        Connection conn=null;
        Statement statement=null;
        ResultSet rs=null;
        List<User> users = new ArrayList<User>();
        try {
            conn = dbTest.getConnection();
            statement=conn.createStatement();
            String sql="SELECT * FROM Users where ((Latitude>"+(latitude-0.05)+") AND (Longitude>"+(longitude-0.05)+")) AND ((Latitude<"+(latitude+0.05)+") AND (Longitude<"+(longitude+0.05)+"))";
            rs = statement.executeQuery(sql);
            while ( rs.next() ) {
                User user = new User();
                user.Id = rs.getInt("Id");
                user.UserName = rs.getString("UserName");
                user.Address1 = rs.getString("Address");
                user.EmailId = rs.getString("Email");
                user.Password = rs.getString("Password");
                user.Pincode1 = rs.getString("Pincode");
                user.RegistrationId = rs.getString("RegistrationId");
                user.RequestsAssigned = rs.getString("RequestsAssigned");
                user.Latitude1 = rs.getFloat("Latitude");
                user.Longitude1 = rs.getFloat("Longitude");
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
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
        return new ArrayList<User>();
    }

    public int UpdateUser(User user){
        Connection conn=null;
        Statement statement=null;
        ResultSet rs=null;
        try {
            conn = dbTest.getConnection();
            statement = conn.createStatement();
            String sql = "UPDATE Users SET UserName="+user.UserName+",Address="+user.Address1+",Pincode="+user.Pincode1+",Email="+user.EmailId+",Latitude="+user.Latitude1+",Longituda="+user.Longitude1+",Password="+user.Password+",RequestsAssigned="+user.RequestsAssigned+",RegistrationId="+user.RegistrationId+" where Id="+user.Id;
            rs = statement.executeQuery(sql);
            return 1;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
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
        return 0;
    }
}
