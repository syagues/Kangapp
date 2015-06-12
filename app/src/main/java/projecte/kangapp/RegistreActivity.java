package projecte.kangapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import projecte.animations.Techniques;
import projecte.animations.YoYo;
import projecte.kangapp.database.ApiConnector;

/**
 * Created by RAÃœL on 18/05/2015.
 */
public class RegistreActivity extends Activity {

    // Log
    protected static final String TAG = "RegistreActivity";

    public EditText etNom;
    public EditText etContrasenya;
    public Button bacces;
    public String pwd;
    public String username;

    // Preferencies
    SharedPreferences prefs;
    String prefsUser = "user";
    int userId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);

        final Button bregistre = (Button) findViewById(R.id.bRegistre);
        etNom = (EditText) findViewById(R.id.etNom);
        etContrasenya = (EditText) findViewById(R.id.etContrasenya);
        bacces = (Button) findViewById(R.id.bAcces);

        etContrasenya.setTypeface(Typeface.DEFAULT);
        etContrasenya.setTransformationMethod(new PasswordTransformationMethod());

        bregistre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDades = new Intent(getApplicationContext(), DadesActivity.class);
                startActivity(iDades);
            }

        });

        bacces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!etContrasenya.getText().toString().equals("") && !etNom.getText().toString().equals("")){

                    username= etNom.getText().toString();
                    pwd = etContrasenya.getText().toString();

                    new isLogedTask().execute(new ApiConnector());
                }
            }
        });

    }

    public void isRegistred(JSONArray jsonAr){
        if (!(jsonAr == null)) {
            JSONObject json = null;

            try {
                json = jsonAr.getJSONObject(0);
                userId = json.getInt("id");
                //Log.i(TAG, "UserId logejat: " + userId);

                // Save Shared Preferences
                new GetUserByIdTask().execute(new ApiConnector());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            YoYo.with(Techniques.Shake).duration(500).playOn(findViewById(R.id.etNom));
            YoYo.with(Techniques.Shake).duration(500).playOn(findViewById(R.id.etContrasenya));
            etNom.setText("");
            etContrasenya.setText("");
            etNom.setBackgroundColor(getResources().getColor(R.color.light_red));
            etContrasenya.setBackgroundColor(getResources().getColor(R.color.light_red));
            Toast.makeText(getApplicationContext(), "Nombre de usuario y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
        }
    }

    public void saveUserPreferences(JSONArray jsonArray){
        SharedPreferences prefs = getSharedPreferences(prefsUser, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        JSONObject json = null;

        try {
            if(jsonArray != null) {
                json = jsonArray.getJSONObject(0);
                Log.i(TAG, "UserId: " + json.getInt("id"));
                editor.putInt("id", json.getInt("id"));
                editor.putString("name", json.getString("name") + " " + json.getString("surname"));
                editor.putString("email", json.getString("email"));
                editor.putString("url", getDownloadUrl(json.getString("path")));

                editor.commit();
                iniciarApp();
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

    public void iniciarApp() {
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    private class isLogedTask  extends AsyncTask<ApiConnector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            //return params[0].GetItemByUserId();
            return params[0].isLoged(username,pwd);
        }

        protected void onPostExecute(JSONArray jsonArray) {
            isRegistred(jsonArray);
        }

    }

    private class GetUserByIdTask extends AsyncTask<ApiConnector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread
            return params[0].GetUserById(userId);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            saveUserPreferences(jsonArray);
        }
    }
}
