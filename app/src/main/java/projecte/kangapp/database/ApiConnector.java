package projecte.kangapp.database;

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

        String url = "http://46.101.24.238/mobile/android/getItemByUserId.php?user_id="+userId+"&locale=es";
        return callWebService(url);
    }

    public JSONArray GetItemDetailsById(int itemId) {

        String url = "http://46.101.24.238/mobile/android/getItemDetailsById.php?item_id="+itemId+"&locale=es";
        return callWebService(url);
    }

    public JSONArray GetUserById(int userId) {

        String url = "http://46.101.24.238/mobile/android/getUserById.php?user_id="+userId;
        return callWebService(url);
    }

    public JSONArray GetUserDetailsById(int userId) {

        String url = "http://46.101.24.238/mobile/android/getUserDetailsById.php?user_id="+userId;
        return callWebService(url);
    }

    public JSONArray GetItemLocationById(int itemId) {

        String url = "http://46.101.24.238/mobile/android/getItemLocationById.php?item_id="+itemId;
        return callWebService(url);
    }

    public JSONArray GetAllItemsLocation() {

        String url = "http://46.101.24.238/mobile/android/getAllItemsLocation.php";
        return callWebService(url);
    }

    public JSONArray GetAllItemCategoriesByLocale(String locale) {

        String url = "http://46.101.24.238/mobile/android/getAllItemCategoriesByLocale.php?locale="+locale;
        return callWebService(url);
    }

    public JSONArray GetAllItemTypesByLocale(String locale) {

        String url = "http://46.101.24.238/mobile/android/getAllItemTypesByLocale.php?locale="+locale;
        return callWebService(url);
    }

    public JSONArray InsertItem(int itemId, int userextend_id, int itemcategory_id, int itemtype_id, String model, String company, double price_day, double price_week, double price_halfmonth, double price_month, double deposit, int state, String extras, double extras_price, String comments, double latitude, double longitude) {

        String url = "http://46.101.24.238/mobile/android/insertItem.php?"
                +"item_id="+itemId
                +"&userextend_id="+userextend_id
                +"&itemcategory_id="+itemcategory_id
                +"&itemtype_id="+itemtype_id
                +"&model="+model
                +"&company="+company
                +"&price_day="+price_day
                +"&price_week="+price_week
                +"&price_halfmonth="+price_halfmonth
                +"&price_month="+price_month
                +"&deposit="+deposit
                +"&state="+state
                +"&extras="+extras
                +"&extras_price="+extras_price
                +"&comments="+comments
                +"&latitude="+latitude
                +"&longitude="+longitude;

        return callWebService(url);
    }

    public JSONArray updateUserDetails(int userId, String nombre, String apellidos, String sexo, String nacimiento, String telefono, int pais, String localidad, String codigoPostal, int mostrar, int info, String recomenViaj, String gustViaj, String hobbies, String biografia, String facebook, String twitter, String googlePlus, String idioma, String nivel) {

        String url = "http://46.101.24.238/mobile/android/updateUserDetails.php?userId="+userId+
                "&name="+nombre+
                "&surname="+apellidos+
                "&gender="+sexo+
                "&datebirth="+nacimiento+
                "&phone_number="+telefono+
                "&country="+pais+
                "&city="+localidad+
                "&xip_code="+codigoPostal+
                "&want_show_city="+mostrar+
                "&want_inform_city="+info+
                "&destination_suggesstions="+recomenViaj+
                "&destination_wishes="+gustViaj+
                "&hobbies="+hobbies+
                "&biography="+biografia+
                "&facebook="+facebook+
                "&twitter="+twitter+
                "&googlePlus="+googlePlus;

        return callWebService(url);
    }

    public JSONArray GetAllCountries() {

        String url = "http://46.101.24.238/mobile/android/getAllCountries.php";
        return callWebService(url);
    }

    public JSONArray GetItemsByLocale(String locale) {

        String url = "http://46.101.24.238/mobile/android/getItemsByLocale.php?locale="+locale;
        return callWebService(url);
    }

    public JSONArray GetItemsAsKangerByUserId(int userId, String locale) {

        String url = "http://46.101.24.238/mobile/android/getItemsAsKangerByUserId.php?user_id="+userId+"&locale="+locale;
        return callWebService(url);
    }

    public JSONArray GetItemsAsArrenderByUserId(int userId, String locale) {

        String url = "http://46.101.24.238/mobile/android/getItemsAsArrenderByUserId.php?user_id="+userId+"&locale="+locale;
        return callWebService(url);
    }

    public JSONArray GetNextItemId() {

        String url = "http://46.101.24.238/mobile/android/getNextItemId.php";
        return callWebService(url);
    }

    public JSONArray GetUserIdById(int userId) {

        String url = "http://46.101.24.238/mobile/android/getUserIdById.php?user_id="+userId;
        return callWebService(url);
    }

    public JSONArray InsertDeal(int itemId, String status, String price, String deposit, String startDate, String endDate, int kangerId, int idKanger, int arrenderId, int idArrender, String subject) {

        String url = "http://46.101.24.238/mobile/android/insertDeal.php?"
                +"item_id="+itemId
                +"&status="+status
                +"&price="+price
                +"&deposit="+deposit
                +"&start_date="+startDate
                +"&end_date="+endDate
                +"&kanger_id="+kangerId
                +"&id_kanger="+idKanger
                +"&arrender_id="+arrenderId
                +"&id_arrender="+idArrender
                +"&subject="+subject;

        return callWebService(url);
    }

    public JSONArray isLoged(String username, String password) {

        String url = "http://46.101.24.238/mobile/android/isLoged.php?username="+username+"&password="+password;
        return callWebService(url);
    }

    public JSONArray setData(String username, String password, String mail, String name, String surname) {

        String url = "http://46.101.24.238/mobile/android/setData.php?username="+username+"&password="+password+"&mail="+mail+"&name="+name+"&surname="+surname;
        return callWebService(url);
    }

    public JSONArray GetItemsByCompanyName(String company, String locale) {

        String url = "http://46.101.24.238/mobile/android/getItemsByLocale.php?locale="+locale+"&company="+company;
        return callWebService(url);
    }

    public JSONArray GetItemsByTypeName(String type, String locale) {

        String url = "http://46.101.24.238/mobile/android/getItemsByLocale.php?locale="+locale+"&type="+type;
        return callWebService(url);
    }

    public JSONArray GetItemsByCategoryName(String category, String locale) {

        String url = "http://46.101.24.238/mobile/android/getItemsByLocale.php?locale="+locale+"&category="+category;
        return callWebService(url);
    }

    public JSONArray callWebService(String url){

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
