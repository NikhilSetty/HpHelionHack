package org.hphelion.heliohelp.Interfaces;


import org.hphelion.heliohelp.Model.Response;

import java.util.List;

/**
 * Created by NJere on 4/23/2015.
 */
public interface IResponseHandler {
    int AddResponse(Response response);
    List<Response> RetrieveResponse(int requestId);
    //Response RetrieveResponse(int id);
    //List<Response> RetrieveResponsesForARequest(int id, int lastResponseId);
    //void DeleteResponse(Response response);
}
