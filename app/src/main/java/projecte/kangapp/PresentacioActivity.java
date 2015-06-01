package projecte.kangapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import projecte.animations.Techniques;
import projecte.animations.YoYo;

/**
 * Created by sergi on 15/5/15.
 */
public class PresentacioActivity extends Activity {

    // Log
    protected static final String TAG = "PresentacioActivity";

    // Preferencies
    SharedPreferences prefs;
    String prefsUser = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacio);

        new GetUserByIdTask().execute(new ApiConnector());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                YoYo.with(Techniques.FadeOutUp)
                        .duration(700)
                        .playOn(findViewById(R.id.imageView));

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        iniciarApp();
                    }
                }, 1000);
            }
        }, 2000);
    }

    public void iniciarApp() {
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    public void saveUserPreferences(JSONArray jsonArray){
        SharedPreferences prefs = getSharedPreferences(prefsUser, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        JSONObject json = null;

        try {
            if(jsonArray != null) {
                json = jsonArray.getJSONObject(0);
                editor.putInt("id", json.getInt("id"));
                editor.putString("name", json.getString("name") + " " + json.getString("surname"));
                editor.putString("email", json.getString("email"));
                editor.putString("url", getDownloadUrl(json.getString("path")));

                editor.commit();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getDownloadUrl(String path){
        String[] pathSplit = path.split("/");
        String url = "http://46.101.24.238";
        for (int i=0; i<pathSplit.length; i++){
            if(i>4){
                url += "/" + pathSplit[i];
            }
        }
        return url;
    }

    private class GetUserByIdTask extends AsyncTask<ApiConnector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread
            return params[0].GetUserById(1);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            saveUserPreferences(jsonArray);
        }
    }
}
