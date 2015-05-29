package projecte.kangapp;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

public class ApiConnector {


    public JSONArray GetItemByUserId(int userId) {

        // URL for getting all customers
        String url = "http://46.101.24.238/mobile/android/getItemByUserId.php?user_id="+userId+"&locale=es";
        // Get HttpResponse Object from url.
        // Get HttpEntity from Http Response Object

        HttpEntity httpEntity = null;

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();

        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            e.printStackTrace();
            //Log Errors Here

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null) {
            try {
                String entityResponse = EntityUtils.toString(httpEntity);
                Log.e("Entity Response  : ", entityResponse);
                jsonArray = new JSONArray(entityResponse);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonArray;
    }

    public JSONArray GetItemDetailsById(int itemId) {

        // URL for getting all customers
        String url = "http://46.101.24.238/mobile/android/getItemDetailsById.php?item_id="+itemId+"&locale=es";
        // Get HttpResponse Object from url.
        // Get HttpEntity from Http Response Object

        HttpEntity httpEntity = null;

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();

        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            e.printStackTrace();
            //Log Errors Here

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null) {
            try {
                String entityResponse = EntityUtils.toString(httpEntity);
                Log.e("Entity Response  : ", entityResponse);
                jsonArray = new JSONArray(entityResponse);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonArray;
    }

}
