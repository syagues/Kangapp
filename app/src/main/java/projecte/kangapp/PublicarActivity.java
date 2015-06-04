package projecte.kangapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Raül.
 */
public class PublicarActivity extends AppCompatActivity implements
        ConnectionCallbacks, OnConnectionFailedListener {

    // Log
    protected static final String TAG = "PublicarActivity";

    // Toolbar
    Bundle savedInstanceState = null;

    // Localitzacio
    GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    boolean locActivat = false;
    boolean locSeleccionat = false;

    // Locale
    String locale = "es";

    // Preferencies
    String prefsUser = "user";

    // Spinners
    int indexCategoria, indexTipo;
    ArrayList<Integer> categoriesId, typesId;

    // Publicar
    int userextend_id, itemcategory_id, itemtype_id;
    double price_day, price_week, price_halfmonth, price_month, deposit, extras_price, latitude, longitude;
    String model, company, extras, comments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar);

        setupBackButton();
        buildGoogleApiClient();

        new GetAllItemCategoriesByLocaleTask().execute(new ApiConnector());
        new GetAllItemTypesByLocaleTask().execute(new ApiConnector());

        // Publicar button
        ImageButton publicarButton = (ImageButton)findViewById(R.id.okButton);
        publicarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publicar();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_publicar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_location:
                if(!locActivat) {
                    item.setIcon(getResources().getDrawable(R.drawable.ic_gps_fixed_green_24dp));
                    item.setTitle(getResources().getString(R.string.str_desactivar_loc));
                    ((ActionMenuItemView) findViewById(R.id.action_selec_location)).setIcon(getResources().getDrawable(R.drawable.ic_place_white_24dp));
                    locSeleccionat = false;
                    mGoogleApiClient.connect();
                } else {
                    item.setIcon(getResources().getDrawable(R.drawable.ic_gps_off_white_24dp));
                    item.setTitle(getResources().getString(R.string.str_activar_loc));
                    mGoogleApiClient.disconnect();
                    Toast.makeText(this, "Localización desactivada", Toast.LENGTH_LONG).show();
                }
                locActivat = !locActivat;
                return true;
            case R.id.action_photos:
                return true;
            case R.id.action_selec_location:
                Intent intent = new Intent(getApplicationContext(), SelecLocationActivity.class);
                startActivityForResult(intent, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode== 1) {
            if (resultCode==RESULT_OK){
                latitude = data.getDoubleExtra("Latitude",0);
                longitude = data.getDoubleExtra("Longitude",0);
                Toast.makeText(this, "Posicion: " + Double.toString(data.getDoubleExtra("Latitude",0)) + ", " + Double.toString(data.getDoubleExtra("Longitude",0)), Toast.LENGTH_SHORT).show();
                if(!locSeleccionat){
                    ((ActionMenuItemView) findViewById(R.id.action_selec_location)).setIcon(getResources().getDrawable(R.drawable.ic_place_green_24dp));
                    locSeleccionat = true;
                }
                if(locActivat){
                    ActionMenuItemView menuItem = ((ActionMenuItemView) findViewById(R.id.action_location));
                    menuItem.setIcon(getResources().getDrawable(R.drawable.ic_gps_off_white_24dp));
                    menuItem.setTitle(getResources().getString(R.string.str_activar_loc));
                    locActivat = false;
                }
            } else {
//                Toast.makeText(this, "Resultat incorrecte", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, R.string.msg_activity_no_contr, Toast.LENGTH_SHORT).show();
        }
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

    /**
     * Localitzacio
     * Es crea una instacia per connectar a GoogleApiClient, on, en cas de connectar correctament
     * executa el mètode "onConnected".
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            Toast.makeText(this, "Posición: " + mLastLocation.getLatitude() + ", " + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No data about Location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    public void publicar(){

        SharedPreferences prefs = getSharedPreferences(prefsUser, MODE_PRIVATE);

        // userextend_id
        userextend_id = prefs.getInt("id", 0);
        // itemcategory_id
        itemcategory_id = categoriesId.get(indexCategoria);
        // itemtype_id
        itemtype_id = typesId.get(indexTipo);
        // model
        if(((EditText)findViewById(R.id.et_modelo)).getText().toString().equals(""))
            model = null;
        else
            model = ((EditText)findViewById(R.id.et_modelo)).getText().toString();
        // company
        if(((EditText)findViewById(R.id.et_marca)).getText().toString().equals(""))
            company = null;
        else
            company = ((EditText)findViewById(R.id.et_marca)).getText().toString();
        // price_day
        if(((EditText) findViewById(R.id.et_precio1)).getText().toString().equals(""))
            price_day = 0;
        else
            price_day = Double.parseDouble(((EditText) findViewById(R.id.et_precio1)).getText().toString());
        // price_week
        if(((EditText) findViewById(R.id.et_precio2)).getText().toString().equals(""))
            price_week = 0;
        else
            price_week = Double.parseDouble(((EditText) findViewById(R.id.et_precio2)).getText().toString());
        // price_halfmonth
        if(((EditText) findViewById(R.id.et_precio3)).getText().toString().equals(""))
            price_halfmonth = 0;
        else
            price_halfmonth = Double.parseDouble(((EditText) findViewById(R.id.et_precio3)).getText().toString());
        // price_month
        if(((EditText) findViewById(R.id.et_precio4)).getText().toString().equals(""))
            price_month = 0;
        else
            price_month = Double.parseDouble(((EditText) findViewById(R.id.et_precio4)).getText().toString());
        // deposit
        if(((EditText) findViewById(R.id.et_deposito)).getText().toString().equals(""))
            deposit = 0;
        else
            deposit = Double.parseDouble(((EditText) findViewById(R.id.et_deposito)).getText().toString());
        // extras
        if(((EditText)findViewById(R.id.et_extras)).getText().toString().equals(""))
            extras = null;
        else
            extras = ((EditText)findViewById(R.id.et_extras)).getText().toString();
        // extras_price
        if(((EditText) findViewById(R.id.et_precio_extras)).getText().toString().equals("")) {
            extras_price = 0;
        } else {
            Log.i(TAG, "Precio extras: " + ((EditText) findViewById(R.id.et_precio_extras)).getText().toString());
            extras_price = Double.parseDouble(((EditText) findViewById(R.id.et_precio_extras)).getText().toString());
        }
        // comments
        if(((EditText)findViewById(R.id.et_comentarios)).getText().toString().equals(""))
            comments = null;
        else
            comments = ((EditText)findViewById(R.id.et_comentarios)).getText().toString();

        if(model != null && company != null && price_day != 0 && price_week != 0 && price_halfmonth != 0 && price_month != 0 && deposit != 0 && (locActivat || locSeleccionat))
            new InsertItemTask().execute(new ApiConnector());
        else
            Toast.makeText(getApplicationContext(), "Marca, modelo, precios y localización son necesarios para publicar un nuevo articulo", Toast.LENGTH_SHORT).show();
    }

    public void setUpCategorySpinner(JSONArray jsonArray){

        ArrayList<String> categories = new ArrayList<>();
        categoriesId = new ArrayList<>();

        JSONObject json = null;
        if(jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    json = jsonArray.getJSONObject(i);
                    categories.add(json.getString("name"));
                    categoriesId.add(json.getInt("translatable_id"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        Spinner sp_cat = (Spinner) findViewById(R.id.sp_categoria);
        ArrayAdapter<String> sp_AA_Uso = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        sp_AA_Uso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_cat.setAdapter(sp_AA_Uso);
        sp_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indexCategoria = parent.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setUpTypeSpinner(JSONArray jsonArray){

        ArrayList<String> types = new ArrayList<>();
        typesId = new ArrayList<>();

        JSONObject json = null;
        if(jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    json = jsonArray.getJSONObject(i);
                    types.add(json.getString("name"));
                    typesId.add(json.getInt("translatable_id"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        Spinner sp_tipo = (Spinner) findViewById(R.id.sp_tipo);
        ArrayAdapter<String> sp_AA_Que = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types);
        sp_AA_Que.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_tipo.setAdapter(sp_AA_Que);
        sp_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indexTipo = parent.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private class GetAllItemCategoriesByLocaleTask extends AsyncTask<ApiConnector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            // it is executed on Background thread
            return params[0].GetAllItemCategoriesByLocale(locale);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            setUpCategorySpinner(jsonArray);
        }
    }

    private class GetAllItemTypesByLocaleTask extends AsyncTask<ApiConnector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            // it is executed on Background thread
            return params[0].GetAllItemTypesByLocale(locale);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            setUpTypeSpinner(jsonArray);
        }
    }

    private class InsertItemTask extends AsyncTask<ApiConnector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            // it is executed on Background thread
            return params[0].InsertItem(userextend_id,itemcategory_id,itemtype_id,model,company,price_day,price_week,price_halfmonth,price_month,deposit,2,extras,extras_price,comments,latitude,longitude);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            Log.i(TAG,"Publicat!!");
            Toast.makeText(getApplicationContext(), "Articulo Publicado!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }
}
