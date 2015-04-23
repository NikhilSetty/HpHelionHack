package org.hphelion.heliohelp;

import jdk.nashorn.internal.ir.CatchNode;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;




import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.net.URL;




public class GCM {

    public boolean SendGCM(String value){
        String GoogleAppID = "AIzaSyC3XOYYMdpMNeoO7oXSrwqZpFCEAnwSwuQ";
        //String regId="APA91bGM8QQJREp7AeLRRD7T_KrmkiqS1NWG-Q6DP_bPNXpM5GSphZLCRskkE_XB_7toYW-jjehDlqx9-2Xc1UaqQ8ukEwvuAtRTO2bVEPld-xvvJZ5VvwEUo-yAypksEZGqLN-x21imi0kVNFfy3oqOYw3hPH4WBw";
        String url="https://android.googleapis.com/gcm/send";
        try{
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            String type="3";
            con.setRequestMethod("POST");
           // String value1 = "{\"data\":{\"Type\":"+type+",\"userName\":\"Nikhil\",\"requestId\":3,\"message\":\"hello there\"},\"registration_ids\":[\""+regId+"\"]}";
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "key="+GoogleAppID);

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(value);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            if(responseCode==200)
                return true;
           //System.out.println("\nSending 'POST' request to URL : " + url);
           //System.out.println("Post parameters : " + value1);
           //System.out.println("Response Code : " + responseCode);


           //BufferedReader in = new BufferedReader(
           //        new InputStreamReader(con.getInputStream()));
           //String inputLine;
           //StringBuffer res = new StringBuffer();

           //while ((inputLine = in.readLine()) != null) {
           //    res.append(inputLine);
           //}
           //in.close();
        }
        catch (IOException ex)
        {
            return false;
        }


        return false;
        //print result

    }

}
