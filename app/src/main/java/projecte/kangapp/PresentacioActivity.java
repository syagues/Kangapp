package projecte.kangapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import projecte.animations.Techniques;
import projecte.animations.YoYo;
import projecte.kangapp.database.ApiConnector;

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

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                YoYo.with(Techniques.FadeOutUp)
                        .duration(700)
                        .playOn(findViewById(R.id.imageView));

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        isLogged();
                    }
                }, 1000);
            }
        }, 2000);
    }

    public void isLogged(){
        SharedPreferences prefs = getSharedPreferences(prefsUser, MODE_PRIVATE);
        int userId = prefs.getInt("id", 0);
        if(userId == 0){
            iniciarApp(RegistreActivity.class);
        } else {
            iniciarApp(PrincipalActivity.class);
        }
    }

    public void iniciarApp(Class clase) {
        Intent intent = new Intent(this, clase);
        startActivity(intent);
        finish();
    }
}
