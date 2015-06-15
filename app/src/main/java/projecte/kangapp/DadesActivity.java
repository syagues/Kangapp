package projecte.kangapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
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
 * Created by RAÜL on 18/05/2015.
 */

public class DadesActivity extends Activity {

    // Log
    protected static final String TAG = "DadesActivity";

    boolean eNom = false;
    boolean eCognom = false;
    boolean eContras = false;
    boolean eNick = false;
    boolean eCorreu = false;

    public EditText etNick;
    public EditText etCorreu;

    public String mail;
    public String name;
    public String pwd;
    public String surname;
    public String nick;
    public String usuariValidat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dades);

        final Button benvDades = (Button) findViewById(R.id.bEnviarDades);
        final EditText etNom = (EditText) findViewById(R.id.etNomDades);
        final EditText etContrasenya = (EditText) findViewById(R.id.etContrasenyaDades);
        etContrasenya.setTypeface(Typeface.DEFAULT);
        etContrasenya.setTransformationMethod(new PasswordTransformationMethod());
        etCorreu = (EditText) findViewById(R.id.etCorreuElectronicDades);
        final EditText etConfCont = (EditText) findViewById(R.id.etConfirmaContrasenya);
        etConfCont.setTypeface(Typeface.DEFAULT);
        etConfCont.setTransformationMethod(new PasswordTransformationMethod());
        etNick = (EditText) findViewById(R.id.etNick);
        final EditText etCognoms = (EditText) findViewById(R.id.etApellido);

        etCorreu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (etCorreu.getText().toString().length() != 0) {
                    if (!isValidEmail(etCorreu.getText().toString())) {
                        etCorreu.setBackgroundColor(getResources().getColor(R.color.light_red));
                    } else {
                        etCorreu.setBackgroundColor(getResources().getColor(R.color.light_green));
                        eCorreu = true;
                    }
                } else eCorreu = false;
                if (eNom && eCognom && eContras && eCorreu && eNick) {
                    benvDades.setVisibility(View.VISIBLE);
                } else benvDades.setVisibility(View.INVISIBLE);
            }
        });

        etNom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (etNom.getText().toString().length() != 0) {
                    eNom = true;
                } else eNom = false;
                if (eNom && eCognom && eContras && eCorreu && eNick) {
                    benvDades.setVisibility(View.VISIBLE);
                } else benvDades.setVisibility(View.INVISIBLE);
            }
        });

        etCognoms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etCognoms.getText().toString().length() != 0) {
                    eCognom = true;
                } else eCognom = false;
                if (eNom && eCognom && eContras && eCorreu && eNick) {
                    benvDades.setVisibility(View.VISIBLE);
                } else benvDades.setVisibility(View.INVISIBLE);
            }
        });

        etNick.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etNick.getText().toString().length() != 0) {
                    etNick.setBackgroundColor(getResources().getColor(android.R.color.white));
                    eNick = true;
                } else eNick = false;
                if (eNom && eCognom && eContras && eCorreu && eNick) {
                    benvDades.setVisibility(View.VISIBLE);

                } else {
                    benvDades.setVisibility(View.INVISIBLE);

                }
            }
        });

        etConfCont.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etContrasenya.getText().toString().length() != 0 && etContrasenya.getText().toString().equals(etConfCont.getText().toString())) {
                    etContrasenya.setBackgroundColor(getResources().getColor(R.color.light_green));
                    etConfCont.setBackgroundColor(getResources().getColor(R.color.light_green));
                    eContras = true;
                } else {
                    etConfCont.setBackgroundColor(getResources().getColor(R.color.light_red));
                    eContras = false;
                }
                if (eNom && eCognom && eContras && eCorreu && eNick) {
                    benvDades.setVisibility(View.VISIBLE);
                } else {
                    benvDades.setVisibility(View.INVISIBLE);
                }
            }
        });

        benvDades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nick = etNick.getText().toString();
                pwd = etContrasenya.getText().toString();
                name = etNom.getText().toString();
                mail = etCorreu.getText().toString();
                surname = etCognoms.getText().toString();

                new setDataTask().execute(new ApiConnector());
            }
        });


    }

    public void isValidated(JSONArray jsonAr) {
        if ((jsonAr == null)) {
            Toast.makeText(getApplicationContext(), "El nombre de usuario ya existe", Toast.LENGTH_LONG).show();
            YoYo.with(Techniques.Shake).duration(500).playOn(findViewById(R.id.etNick));
            etNick.setBackgroundColor(getResources().getColor(R.color.light_red));
        } else {
            JSONObject json = null;

            try {
                json = jsonAr.getJSONObject(0);
                if (json.has("id")) {
                    Toast.makeText(getApplicationContext(), "Registro completado!", Toast.LENGTH_LONG).show();
                    finish();
                }
                if (json.has("email")) {
                    Toast.makeText(getApplicationContext(), "El correo electrónico introducido ya esta registrado", Toast.LENGTH_LONG).show();
                    YoYo.with(Techniques.Shake).duration(500).playOn(findViewById(R.id.etCorreuElectronicDades));
                    etCorreu.setBackgroundColor(getResources().getColor(R.color.light_red));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private class setDataTask extends AsyncTask<ApiConnector, Long, JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            //return params[0].GetItemByUserId();
            return params[0].setData(nick, pwd, mail, name, surname);
        }

        protected void onPostExecute(JSONArray jsonArray) {
            isValidated(jsonArray);
        }

    }
}