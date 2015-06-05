package projecte.kangapp;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import projecte.kangapp.adapter.DatePickerFragment;

/**
 * Created by sergi on 22/5/15.
 */
public class EditarPerfilActivity extends AppCompatActivity {

    // Log
    protected static final String TAG = "EditarPerfilActivity";

    // Nacimiento
    EditText edNacimiento;
    RadioButton rbHombre, rbMujer;

    // Preferencies
    String prefsUser = "user";
    int userId;

    // Formulari
    EditText edNombre, edApellidos, edTelefono, edLocalidad, edCodigoPostal, edRecomenViaj, edGustViaj, edHobbies, edBiografia, edFacebook, edTwitter, edGooglePlus, edIdioma, edNivel;
    Spinner spPais;
    CheckBox cbMostrar, cbInfo;

    String nombre, apellidos, sexo, telefono, localidad, codigoPostal, recomenViaj, gustViaj, hobbies, biografia, facebook, twitter, googlePlus, idioma, nivel, nacimiento;
    int mostrar, info, dia, mes, any, pais;

    // Spinner
    int indexCountry;
    ArrayList<Integer> countriesId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicialitzacio spinner
        new GetAllCountriesTask().execute(new ApiConnector());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_editar_perfil);

        // Toolbar
        setupBackButton();

        // RadioButtons
        rbHombre = (RadioButton) findViewById(R.id.ep_rb_hombre);
        rbMujer = (RadioButton) findViewById(R.id.ep_rb_mujer);
        rbHombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbMujer.setChecked(false);
            }
        });
        rbMujer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbHombre.setChecked(false);
            }
        });

        // Nacimiento
        edNacimiento = (EditText) findViewById(R.id.ep_et_nacimiento);
        edNacimiento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.i(TAG, "Naixement onFocus listener");
                    showDatePicker();
                }
            }
        });
        edNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Naixement onClick listener");
                showDatePicker();
            }
        });

        // SaveButton
        ImageButton saveButton = (ImageButton) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        date.setCallBack(onDate);
        date.show(getSupportFragmentManager(), "Date Picker");
    }

    OnDateSetListener onDate = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dia = dayOfMonth;
            mes = monthOfYear + 1;
            any = year;
            edNacimiento.setText(dayOfMonth + "/" + mes + "/" + year);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editar_perfil, menu);

        return true;
    }

    public void setupBackButton(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void setView(JSONArray jsonArray) {
        edNombre = (EditText) findViewById(R.id.ep_et_nombre);
        edApellidos = (EditText) findViewById(R.id.ep_et_apellidos);
        edNacimiento = (EditText) findViewById(R.id.ep_et_nacimiento);
        edTelefono = (EditText) findViewById(R.id.ep_et_telefono);
        spPais = (Spinner) findViewById(R.id.ep_sp_pais);
        edLocalidad = (EditText) findViewById(R.id.ep_et_localidad);
        edCodigoPostal = (EditText) findViewById(R.id.ep_et_codpostal);
        cbMostrar = (CheckBox) findViewById(R.id.cb_mostrar);
        cbInfo = (CheckBox) findViewById(R.id.cb_informar);
        edRecomenViaj = (EditText) findViewById(R.id.ep_et_recom_viaj);
        edGustViaj = (EditText) findViewById(R.id.ep_et_gust_viaj);
        edHobbies = (EditText) findViewById(R.id.ep_et_hobbies);
        edBiografia = (EditText) findViewById(R.id.ep_et_biografia);
        edFacebook = (EditText) findViewById(R.id.ep_et_facebook);
        edTwitter = (EditText) findViewById(R.id.ep_et_twitter);
        edGooglePlus = (EditText) findViewById(R.id.ep_et_googleplus);
        edIdioma = (EditText) findViewById(R.id.ep_et_idioma);
        edNivel = (EditText) findViewById(R.id.ep_et_nivel);

        JSONObject json = null;
        try {
            if(jsonArray != null){

                json = jsonArray.getJSONObject(0);

                // Nombre
                edNombre.setText(json.getString("name"));
                // Apellidos
                edApellidos.setText(json.getString("surname"));
                // Sexo
                if(json.getInt("gender") == 0)
                    rbHombre.setChecked(true);
                else
                    rbMujer.setChecked(true);
                // Nacimiento
                if(!json.getString("datebirth").equals("null")) {

                    String[] datetime = json.getString("datebirth").split(" ");
                    String[] date = datetime[0].split("-");
                    dia = Integer.parseInt(date[2]);
                    mes = Integer.parseInt(date[1]);
                    any = Integer.parseInt(date[0]);
                    edNacimiento.setText(dia + "/" + mes + "/" + any);
                }
                // Telefono
                if(!json.getString("phone_number").equals("null"))
                    edTelefono.setText(json.getString("phone_number"));
                // Pais
                if(!json.getString("geo").equals("null"))
                    spPais.setSelection(json.getInt("geo_id")-1);
                // Localidad
                if(!json.getString("location").equals("null"))
                    edLocalidad.setText(json.getString("location"));
                // Codigo postal
                if(!json.getString("xip_code").equals("null"))
                    edCodigoPostal.setText(json.getString("xip_code"));
                // Mostrar ciudad
                if(json.getInt("want_show_city") == 1)
                    cbMostrar.setChecked(true);
                // Informar ciudad
                if(json.getInt("want_inform_city") == 1)
                    cbInfo.setChecked(true);
                // Recomenda viajar
                if(!json.getString("destination_suggesstions").equals("null"))
                    edRecomenViaj.setText(json.getString("destination_suggesstions"));
                // Gustaria viajar
                if(!json.getString("destination_wishes").equals("null"))
                    edGustViaj.setText(json.getString("destination_wishes"));
                // Hobbies
                if(!json.getString("hobbies").equals("null"))
                    edHobbies.setText(json.getString("hobbies"));
                // Biografia
                if(!json.getString("biography").equals("null"))
                    edBiografia.setText(json.getString("biography"));
                // Facebook
                if(!json.getString("facebook").equals("null"))
                    edFacebook.setText(json.getString("facebook"));
                // Twitter
                if(!json.getString("twitter").equals("null"))
                    edTwitter.setText(json.getString("twitter"));
                // Google plus
                if(!json.getString("googlePlus").equals("null"))
                    edGooglePlus.setText(json.getString("googlePlus"));
                // Idioma
                if(!json.getString("language").equals("null"))
                    edIdioma.setText(json.getString("language"));
                // Nivel
                if(!json.getString("level").equals("null"))
                    edNivel.setText(json.getString("level"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void save(){
        // Nombre
        if(edNombre.getText().toString().equals("")) {
            nombre = null;
        } else {
            String[] nomSplt = edNombre.getText().toString().split(" ");
            if(nomSplt.length > 1){
                nombre = addBars(nomSplt);
            } else {
                nombre = edNombre.getText().toString();
            }
        }
        // Apellidos
        if(edApellidos.getText().toString().equals("")) {
            apellidos = null;
        } else {
            String[] apeSplt = edApellidos.getText().toString().split(" ");
            if(apeSplt.length > 1) {
                apellidos = addBars(apeSplt);
                Log.i(TAG, apellidos);
            } else {
                apellidos = edApellidos.getText().toString();
            }
        }
        // Sexo
        if(rbHombre.isChecked() || rbMujer.isChecked()){
            if(rbHombre.isChecked())
                sexo = "0";
            else
                sexo = "1";
        } else {
            sexo = null;
        }
        // Nacimiento
        if(edNacimiento.getText().toString().equals("")){
            nacimiento = null;
        } else {
            nacimiento = any + "-" + mes + "-" + dia;
        }
        // Telefono
        if(edTelefono.getText().toString().equals(""))
            telefono = null;
        else
            telefono = edTelefono.getText().toString();
        // Pais
        pais = spPais.getSelectedItemPosition()+1;
        // Localidad
        if(edLocalidad.getText().toString().equals("")) {
            localidad = null;
        } else {
            String[] locSplt = edLocalidad.getText().toString().split(" ");
            if(locSplt.length > 1) {
                localidad = addBars(locSplt);
            } else {
                localidad = edLocalidad.getText().toString();
            }
        }
        // Codigo postal
        if(edCodigoPostal.getText().toString().equals(""))
            codigoPostal = null;
        else
            codigoPostal = edCodigoPostal.getText().toString();
        // Mostrar ciudad
        if(cbMostrar.isChecked())
            mostrar = 1;
        else
            mostrar = 0;
        // Informar ciudad
        if(cbInfo.isChecked())
            info = 1;
        else
            info = 0;
        // Recomenda viajar
        if(edRecomenViaj.getText().toString().equals("")) {
            recomenViaj = null;
        } else {
            String[] recSplt = edRecomenViaj.getText().toString().split(" ");
            if(recSplt.length > 1){
                recomenViaj = addBars(recSplt);
            } else {
                recomenViaj = edRecomenViaj.getText().toString();
            }
        }
        // Gustaria viajar
        if(edGustViaj.getText().toString().equals("")) {
            gustViaj = null;
        } else {
            String[] gusSplt = edGustViaj.getText().toString().split(" ");
            if(gusSplt.length > 1) {
                gustViaj = addBars(gusSplt);
            } else {
                gustViaj = edGustViaj.getText().toString();
            }
        }
        // Hobbies
        if(edHobbies.getText().toString().equals("")) {
            hobbies = null;
        } else {
            String[] hobSplt = edHobbies.getText().toString().split(" ");
            if(hobSplt.length > 1) {
                hobbies = addBars(hobSplt);
            } else {
                hobbies = edHobbies.getText().toString();
            }
        }
        // Biografia
        if(edBiografia.getText().toString().equals("")) {
            biografia = null;
        } else {
            String[] bioSplt = edBiografia.getText().toString().split(" ");
            if(bioSplt.length > 1) {
                biografia = addBars(bioSplt);
            } else {
                biografia = edBiografia.getText().toString();
            }
        }
        // Facebook
        if(edFacebook.getText().toString().equals(""))
            facebook = null;
        else
            facebook = edFacebook.getText().toString();
        // Twitter
        if(edTwitter.getText().toString().equals(""))
            twitter = null;
        else
            twitter = edTwitter.getText().toString();
        // Google plus
        if(edGooglePlus.getText().toString().equals(""))
            googlePlus = null;
        else
            googlePlus = edGooglePlus.getText().toString();
        // Idioma
        if(edIdioma.getText().toString().equals(""))
            idioma = null;
        else
            idioma = edIdioma.getText().toString();
        // Nivel
        if(edNivel.getText().toString().equals(""))
            nivel = null;
        else
            nivel = edNivel.getText().toString();

        if(nombre != null && apellidos != null)
            new UpdateUserDetailsTask().execute(new ApiConnector());
        else
            Toast.makeText(getApplicationContext(), "Nombre y Apellidos son necesarios", Toast.LENGTH_SHORT).show();

    }

    public void setUpCountrySpinner(JSONArray jsonArray){

        ArrayList<String> countries = new ArrayList<>();
        countriesId = new ArrayList<>();

        JSONObject json = null;
        if(jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    json = jsonArray.getJSONObject(i);
                    countries.add(json.getString("name"));
                    countriesId.add(json.getInt("id"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        Spinner sp_cat = (Spinner) findViewById(R.id.ep_sp_pais);
        ArrayAdapter<String> sp_AA_Uso = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, countries);
        sp_AA_Uso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_cat.setAdapter(sp_AA_Uso);
        sp_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indexCountry = parent.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Inicialitzem els valors de tots el camps
        new GetUserDetailsByIdTask().execute(new ApiConnector());
    }

    public String addBars(String[] strings){
        String string = "";
        for(int i = 0; i < strings.length; i++){
            if(i == 0)
                string = strings[i];
            else
                string = string + "_" + strings[i];
        }

        return string;
    }

    private class GetUserDetailsByIdTask extends AsyncTask<ApiConnector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread
            SharedPreferences prefs = getSharedPreferences(prefsUser, MODE_PRIVATE);
            userId = prefs.getInt("id", 0);

            return params[0].GetUserDetailsById(userId);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            setView(jsonArray);
        }
    }

    private class GetAllCountriesTask extends AsyncTask<ApiConnector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            // it is executed on Background thread
            return params[0].GetAllCountries();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            setUpCountrySpinner(jsonArray);
        }
    }

    private class UpdateUserDetailsTask extends AsyncTask<ApiConnector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            return params[0].updateUserDetails(userId, nombre, apellidos, sexo, nacimiento, telefono, pais, localidad, codigoPostal, mostrar, info, recomenViaj, gustViaj, hobbies, biografia, facebook, twitter, googlePlus, idioma, nivel);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            Log.i(TAG,"Modificat!!");
            Toast.makeText(getApplicationContext(), "Perfil Guardado!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }
}
