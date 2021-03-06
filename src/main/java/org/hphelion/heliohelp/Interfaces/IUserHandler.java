package org.hphelion.heliohelp.Interfaces;

//import org.hp.samples.Model.Request;
import org.hphelion.heliohelp.Model.User;

import java.sql.SQLException;
import java.util.List;


/**
 * Created by NJere on 4/23/2015.
 */
public interface IUserHandler {
    int AddUser(User user);
    //void UpdateUser(User user);
    int UpdateUser(User user);
    //int CheckUser(String email, String password);
    boolean EmailAlreadyExists(String email);
    User getUser(int userId);
    int UpdateUserReg(int userId,String regId);
    List<User> GetUsers (Float latitude, Float longitude);

    int GetUserId(String emailId, String password);
    //List<Request> RetrieveRequestsGeneratedByAUser(int id);
    //User RetrieveUser(int id);
    //List<User> RetrieveUsersForARequest(Request request);
    //void DeleteUser(User user);
    //List<Request> RetrieveRequestsForANewUser(User user);
}
