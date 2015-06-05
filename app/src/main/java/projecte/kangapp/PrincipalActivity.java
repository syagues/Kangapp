package projecte.kangapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import android.support.v7.widget.*;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.database.MatrixCursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PrincipalActivity extends AppCompatActivity implements
        ConnectionCallbacks, OnConnectionFailedListener {

    // Log
    protected static final String TAG = "PrincipalActivity";

    // Mapes
    private GoogleMap mMap;

    // Localitzacio
    GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    double latitudeActual, longitudeActual;
    float bearingActual, zoomActual, tiltActual;

    // Preferencies
    String prefsLoc = "localitzacio";
    String prefsUser = "user";

    // Cerca
    android.support.v7.widget.SearchView searchView;

    // Toolbar
    Bundle savedInstanceState = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_principal);

        // Toolbar (Menu lateral)
        setupToolbar();

        // Fab button
        setupFabButton();

        // Localitzacio
        buildGoogleApiClient();

        // Publicar button
        ImageButton publicarButton = (ImageButton)findViewById(R.id.publicarButton);
        publicarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PublicarActivity.class);
                startActivity(intent);
            }
        });

        // Articulos button
        ImageButton articulosButton = (ImageButton)findViewById(R.id.articulosButton);
        articulosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ArticulosActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveActualLocation();
    }

    public void setupToolbar(){
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = getSharedPreferences(prefsUser, MODE_PRIVATE);
        //initialize and create the image loader logic
        DrawerImageLoader.init(new DrawerImageLoader.IDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx) {
                return null;
            }
        });

        // Create a few sample profile
        final IProfile profile = new ProfileDrawerItem().withName(prefs.getString("name","Usuario User")).withEmail(prefs.getString("email","usuario@gmail.com")).withIcon(prefs.getString("url","http://kangapp.com/uploads/gallery/undefined.png"));

        // Create the AccountHeader
        AccountHeader.Result headerResult = new AccountHeader()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header_amber)
                .addProfiles(
                        profile
                )
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        Drawer.Result result = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.str_buscar).withIdentifier(1).withIcon(R.drawable.ic_place_orange_36dp).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.str_mis_articulos).withIdentifier(2).withIcon(R.drawable.ic_store_mall_directory_grey600_36dp).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.str_chat).withIdentifier(3).withIcon(R.drawable.ic_chat_grey600_36dp).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.str_publicar).withIdentifier(4).withIcon(R.drawable.ic_add_circle_grey600_36dp).withCheckable(false),
                        new SectionDrawerItem().withName(R.string.str_mis_tratos).withIdentifier(5),
                        new PrimaryDrawerItem().withName(R.string.str_como_kanger).withIcon(R.drawable.ic_local_mall_grey600_36dp).withIdentifier(6).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.str_como_arrend).withIcon(R.drawable.ic_shopping_cart_grey600_36dp).withIdentifier(7).withCheckable(false),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.str_perfil).withIcon(R.drawable.ic_person_grey600_36dp).withIdentifier(8).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.str_ajustes).withIcon(R.drawable.ic_settings_grey600_36dp).withIdentifier(9).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.str_ayuda).withIcon(R.drawable.ic_help_grey600_36dp).withIdentifier(10).withCheckable(false),
                        new DividerDrawerItem()

                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {

                        if (drawerItem != null) {
                            Intent intent = null;
                            switch (drawerItem.getIdentifier()) {
                                case 2:
                                    intent = new Intent(getApplicationContext(), MisArticulosActivity.class);
                                    break;
                                case 3:
                                    intent = new Intent(getApplicationContext(), ChatActivity.class);
                                    break;
                                case 4:
                                    intent = new Intent(getApplicationContext(), PublicarActivity.class);
                                    break;
                                case 6:
                                    intent = new Intent(getApplicationContext(), ComoKangerActivity.class);
                                    break;
                                case 7:
                                    intent = new Intent(getApplicationContext(), ComoArrendatarioActivity.class);
                                    break;
                                case 8:
                                    intent = new Intent(getApplicationContext(), PerfilActivity.class);
                                    break;
                                case 9:
                                    intent = new Intent(getApplicationContext(), AjustesActivity.class);
                                    break;
                                case 10:
                                    intent = new Intent(getApplicationContext(), AyudaActivity.class);
                                    break;
                                default:
                                    break;
                            }
                            if (intent != null) {
                                saveActualLocation();
                                startActivity(intent);
                            }
                        }

                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        //only set the active selection or active profile if we do not recreate the activity
        if (savedInstanceState == null) {
            // set the selection to the item with the identifier 1
            result.setSelectionByIdentifier(1, false);
        }
    }

    public void setupFabButton(){
        ImageButton fabButton = (ImageButton) findViewById(R.id.fabButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getResources().getString(R.string.str_buscar_ciudad));

        setupSearchView();

        return true;
    }

    /**
     * Inicia el mapa si es possible fer-ho (Play services correctes)
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            mMap.setMyLocationEnabled(true);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                new GetAllItemsLocationTask().execute(new ApiConnector());
            }
        } else {
            new GetAllItemsLocationTask().execute(new ApiConnector());
        }
    }

    /**
     * Per afegir Marcadors al Mapa
     */
    private void setUpMap(JSONArray jsonArray) {
        // Netejem el mapa i tornem a afegir els markers
        mMap.clear();
        JSONObject json = null;
        if(jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    json = jsonArray.getJSONObject(i);
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(json.getDouble("latitude"), json.getDouble("longitude")))
                            .title(json.getString("company") + " " + json.getString("model"))
                            .snippet(json.getString("category") + ", " + json.getString("type")));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
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
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
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
            latitudeActual = mLastLocation.getLatitude();
            longitudeActual = mLastLocation.getLongitude();
            goToLocationInitial(latitudeActual, longitudeActual);
        } else {
            Toast.makeText(this, "No data about Location", Toast.LENGTH_LONG).show();
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

    private void setupSearchView() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(TAG, "Query = " + query + " : submitted");
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    if (query.length() > 3) {
                        List<Address> addresses = geocoder.getFromLocationName(query, 1);
                        Address address = null;
                        if (!addresses.isEmpty()) {
                            address = addresses.get(0);
                            double latitude = address.getLatitude();
                            double longitude = address.getLongitude();
                            goToLocation(latitude, longitude);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                Log.i(TAG, "Query final = " + query);
                return false;
            }
        });
    }

    public void goToLocationInitial(double latitude, double longitude) {

        SharedPreferences prefs = getSharedPreferences(prefsLoc, MODE_PRIVATE);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(prefs.getFloat("latitude", (float) latitude), prefs.getFloat("longitude", (float) longitude)))
                .bearing(prefs.getFloat("bearing", 0))
                .tilt(prefs.getFloat("tilt", 0))
                .zoom(prefs.getFloat("zoom", 12))
                .build();

        latitudeActual = prefs.getFloat("latitude", (float) latitude);
        longitudeActual = prefs.getFloat("longitude", (float) longitude);
        bearingActual = prefs.getFloat("bearing", 0);
        zoomActual = prefs.getFloat("zoom", 12);
        tiltActual = prefs.getFloat("tilt", 0);

        mMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(cameraPosition),
                1,
                new GoogleMap.CancelableCallback() {

                    @Override
                    public void onFinish() {
                        saveLocation(latitudeActual, longitudeActual, bearingActual, zoomActual, tiltActual);
                    }

                    @Override
                    public void onCancel() {
                    }
                }
        );

//        Log.i(TAG, "initial latitude " + prefs.getFloat("latitude", (float) latitude));
//        Log.i(TAG, "initial longitude " + prefs.getFloat("longitude", (float) longitude));
//        Log.i(TAG, "initial zoom " + prefs.getFloat("zoom", (float) 12));
    }

    public void goToLocation(double latitude, double longitude){

        latitudeActual = latitude;
        longitudeActual = longitude;
        bearingActual = 0;
        zoomActual = 12;
        tiltActual = 0;

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude))
                .bearing(0)
                .tilt(0)
                .zoom(12)
                .build();

        mMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(cameraPosition),
                2500,
                new GoogleMap.CancelableCallback() {

                    @Override
                    public void onFinish() {
                        saveLocation(latitudeActual, longitudeActual, bearingActual, zoomActual, tiltActual);
                    }

                    @Override
                    public void onCancel() {
                    }
                }
        );

//        Log.i(TAG, "goto latitude " + latitude);
//        Log.i(TAG, "goto longitude " + longitude);
//        Log.i(TAG, "goto zoom " + 12);
    }

    public void saveLocation(double latitude, double longitude, float bearing, float zoom, float tilt){

        SharedPreferences prefs = getSharedPreferences(prefsLoc, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("latitude", (float) latitude);
        editor.putFloat("longitude", (float) longitude);
        editor.putFloat("bearing", bearing);
        editor.putFloat("zoom", zoom);
        editor.putFloat("tilt", tilt);
        editor.commit();

//        Log.i(TAG, "saved latitude " + latitudeActual);
//        Log.i(TAG, "saved longitude " + longitudeActual);
//        Log.i(TAG, "saved zoom " + zoom);
    }

    public void saveActualLocation(){
        CameraPosition mCam = mMap.getCameraPosition();
        saveLocation(mCam.target.latitude, mCam.target.longitude, mCam.bearing, mCam.zoom, mCam.tilt);
    }

    private class GetAllItemsLocationTask extends AsyncTask<ApiConnector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            // it is executed on Background thread
            return params[0].GetAllItemsLocation();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            setUpMap(jsonArray);
        }
    }
}
