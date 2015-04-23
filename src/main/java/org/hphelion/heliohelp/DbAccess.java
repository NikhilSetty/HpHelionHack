package org.hphelion.heliohelp;

import org.hphelion.heliohelp.Interfaces.IDbAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by NJere on 4/22/2015.
 */
public class DbAccess implements IDbAccess {
    Connection conn = null;
    Statement stmt = null;
    public Connection getConnection(){
        final String JDBC_DRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        final String DB_URL="jdbc:sqlserver://tddowsht2o.database.windows.net;databaseName=hike-db";

        //jdbc:microsoft:sqlserver://
        //  Database credentials
        final String USER = "anuj";
        final String PASS = "P@ssw0rd123";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        }
        catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
           // out.println(se);
            return null;
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
            //out.println(e);
            return null;
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();

            }catch(SQLException se2){
                return null;
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
                return null;
              //  out.println(se.getMessage());
            }//end finally try
        }
        return conn;
    }
}
