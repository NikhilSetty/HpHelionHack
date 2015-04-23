package org.hphelion.heliohelp.Interfaces;


import org.hphelion.heliohelp.Model.Response;

/**
 * Created by NJere on 4/23/2015.
 */
public interface IResponseHandler {
    int AddResponse(Response response);
    //Response RetrieveResponse(int id);
    //List<Response> RetrieveResponsesForARequest(int id, int lastResponseId);
    //void DeleteResponse(Response response);
}
