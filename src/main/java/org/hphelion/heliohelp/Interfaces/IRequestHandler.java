package org.hphelion.heliohelp.Interfaces;



import org.hphelion.heliohelp.Model.Request;

import java.sql.SQLException;

/**
 * Created by NJere on 4/22/2015.
 */
public interface IRequestHandler {
    int AddRequest(Request request) throws SQLException;
    void UpdateRequest(Request request) throws SQLException;
    //Request RetrieveRequest(int id);
    //List<Request> RetrieveAllRequests(int id, int lastRequestId);
    //void AddRequestToAUser(int requestId, User user);
    //void AddUsersToARequest(List<Integer> userIds, Request request);
    //void DeleteRequest(int id);
    //void UnassignRequestFromUser(int userId, int requestId);
    //List<Request> RetrieveRequestsForAUser(int id, int lastRequestId);
}
