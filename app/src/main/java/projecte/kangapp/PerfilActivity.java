package projecte.kangapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import scrolls.ObservableScrollView;
import scrolls.ObservableScrollViewCallbacks;
import scrolls.ScrollState;
import scrolls.ScrollUtils;

/**
 * Created by sergi on 20/5/15.
 */
public class PerfilActivity extends AppCompatActivity {

    // Log
    protected static final String TAG = "PerfilActivity";

    // Toolbar
    Bundle savedInstanceState = null;

    private ImageButton mFabButton;
    public static boolean isMiPerfil = false;

    // Preferencies
    String prefsUser = "user";
    int userId;
    boolean perfilPublic = false;
    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        if(this.getIntent().getExtras() != null){
            // Toolbar (Back button)
            setupBackButton();

            Bundle bundle = this.getIntent().getExtras();
            userId = bundle.getInt("userid");
            perfilPublic = true;

            mFabButton = (ImageButton) findViewById(R.id.fabButton);
            mFabButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_chat_white_24dp));
            mFabButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        } else {

            // Toolbar (Menu lateral)
            setupToolbar();

            mFabButton = (ImageButton) findViewById(R.id.fabButton);
            mFabButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_create_white_24dp));
            mFabButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), EditarPerfilActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetUserDetailsByIdTask().execute(new ApiConnector());
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
        final IProfile profile = new ProfileDrawerItem().withName(prefs.getString("name","Usuario User")).withEmail(prefs.getString("email","usuario@gmail.com")).withIcon(prefs.getString("url", "http://kangapp.com/uploads/gallery/undefined.png"));

        // Create the AccountHeader
        AccountHeader.Result headerResult = new AccountHeader()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header_amber
                )
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
                        new PrimaryDrawerItem().withName(R.string.str_buscar).withIdentifier(1).withIcon(R.drawable.ic_place_grey600_36dp).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.str_mis_articulos).withIdentifier(2).withIcon(R.drawable.ic_store_mall_directory_grey600_36dp).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.str_chat).withIdentifier(3).withIcon(R.drawable.ic_chat_grey600_36dp).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.str_publicar).withIdentifier(4).withIcon(R.drawable.ic_add_circle_grey600_36dp).withCheckable(false),
                        new SectionDrawerItem().withName(R.string.str_mis_tratos).withIdentifier(5),
                        new PrimaryDrawerItem().withName(R.string.str_como_kanger).withIcon(R.drawable.ic_local_mall_grey600_36dp).withIdentifier(6).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.str_como_arrend).withIcon(R.drawable.ic_shopping_cart_grey600_36dp).withIdentifier(7).withCheckable(false),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.str_perfil).withIcon(R.drawable.ic_person_orange_36dp).withIdentifier(8).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.str_ajustes).withIcon(R.drawable.ic_settings_grey600_36dp).withIdentifier(9).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.str_ayuda).withIcon(R.drawable.ic_help_grey600_36dp).withIdentifier(10).withCheckable(false),
                        new DividerDrawerItem()

                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {

                        if (drawerItem != null) {
                            Intent intent = null;
                            switch (drawerItem.getIdentifier()){
                                case 1:
                                    intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                                    break;
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
                                case 9:
                                    intent = new Intent(getApplicationContext(), AjustesActivity.class);
                                    break;
                                case 10:
                                    intent = new Intent(getApplicationContext(), AyudaActivity.class);
                                    break;
                                default:
                                    break;
                            }
                            if(intent != null)
                                startActivity(intent);
                        }

                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        //only set the active selection or active profile if we do not recreate the activity
        if (savedInstanceState == null) {
            // set the selection to the item with the identifier 1
            result.setSelectionByIdentifier(8, false);
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

    public void setView(JSONArray jsonArray) {
        ivImage = (ImageView) findViewById(R.id.image);
        TextView tvUserName = (TextView) findViewById(R.id.name);
        TextView tvPuntuation = (TextView) findViewById(R.id.tv_puntuacion);
        TextView tvGeo = (TextView) findViewById(R.id.tv_nombre_pais);
        TextView tvLocation = (TextView) findViewById(R.id.tv_nombre_ciudad);
        TextView tvShowCity = (TextView) findViewById(R.id.tv_quiero_mostrar);
        TextView tvShareInfo = (TextView) findViewById(R.id.tv_quiero_compartir);
        TextView tvBiography = (TextView) findViewById(R.id.tv_txt_biografia);
        TextView tvHobbies = (TextView) findViewById(R.id.tv_txt_hobbies);
        TextView tvRecomendTravel = (TextView) findViewById(R.id.tv_nombre_recom);
        TextView tvWishTravel = (TextView) findViewById(R.id.tv_nombre_gust);
        TextView tvFacebook = (TextView) findViewById(R.id.tv_txt_facebook);
        TextView tvTwitter = (TextView) findViewById(R.id.tv_txt_twitter);
        TextView tvGooglePlus = (TextView) findViewById(R.id.tv_txt_googleplus);
        TextView tvLang = (TextView) findViewById(R.id.tv_idioma1);
        TextView tvLangLevel = (TextView) findViewById(R.id.tv_nivel1);

        JSONObject json = null;
        try {
            if(jsonArray != null){

                json = jsonArray.getJSONObject(0);

                LoadImageFromURL loadImage = new LoadImageFromURL();
                loadImage.execute(getDownloadUrl(json.getString("path")));
                tvUserName.setText(json.getString("name") + " " + json.getString("surname"));
                // Puntuacion
                if(json.getString("rate") != "null")
                    tvPuntuation.setText(Float.toString((float)json.getDouble("rate")/2));
                // Pais
                if(json.getString("geo") != "null") {
                    tvGeo.setText(json.getString("geo"));
                } else {
                    findViewById(R.id.ic_pais).setVisibility(View.GONE);
                    findViewById(R.id.tv_pais).setVisibility(View.GONE);
                    tvGeo.setVisibility(View.GONE);
                }
                // Ciudad
                if(json.getString("location") != "null") {
                    tvLocation.setText(json.getString("location"));
                } else {
                    findViewById(R.id.ic_ciudad).setVisibility(View.GONE);
                    findViewById(R.id.tv_ciudad).setVisibility(View.GONE);
                    tvLocation.setVisibility(View.GONE);
                }
                // Quiero mostrar ciudad
                if(json.getInt("want_show_city") != 1)
                    tvShowCity.setVisibility(View.GONE);
                // Quiero informar ciudad
                if(json.getInt("want_inform_city") != 1)
                    tvShareInfo.setVisibility(View.GONE);
                // Biografia
                if(json.getString("biography") != "null") {
                    tvBiography.setText(json.getString("biography"));
                } else {
                    findViewById(R.id.ic_biografia).setVisibility(View.GONE);
                    findViewById(R.id.tv_biografia).setVisibility(View.GONE);
                    tvBiography.setVisibility(View.GONE);
                }
                // Control Tarjeta Perfil
                if(json.getString("geo").equals("null") && json.getString("location").equals("null") && json.getString("want_show_city").equals("0") && json.getString("want_inform_city").equals("0") && json.getString("biography").equals("null"))
                    findViewById(R.id.card_perfil).setVisibility(View.GONE);
                // Hobbies
                if(json.getString("hobbies") != "null") {
                    tvHobbies.setText(json.getString("hobbies"));
                } else {
                    findViewById(R.id.ic_hobbies).setVisibility(View.GONE);
                    findViewById(R.id.tv_hobbies).setVisibility(View.GONE);
                    tvHobbies.setVisibility(View.GONE);
                }
                // Donde recomiendo viajar
                if(json.getString("destination_suggesstions") != "null") {
                    tvRecomendTravel.setText(json.getString("destination_suggesstions"));
                } else {
                    findViewById(R.id.tv_recom_viaj).setVisibility(View.GONE);
                    tvRecomendTravel.setVisibility(View.GONE);
                }
                // Donde me gustaria viajar
                if(json.getString("destination_wishes") != "null") {
                    tvWishTravel.setText(json.getString("destination_wishes"));
                } else {
                    findViewById(R.id.tv_gust_viaj).setVisibility(View.GONE);
                    tvWishTravel.setVisibility(View.GONE);
                }
                if(json.getString("destination_suggesstions") == "null" && json.getString("destination_wishes") == "null")
                    findViewById(R.id.ic_viajar).setVisibility(View.GONE);
                // Control Tarjeta Intereses Personales
                if(json.getString("hobbies").equals("null") && json.getString("destination_suggesstions").equals("null") && json.getString("destination_wishes").equals("null"))
                    findViewById(R.id.card_intereses).setVisibility(View.GONE);
                // Facebook
                if(json.getString("facebook") != "null") {
                    tvFacebook.setText(json.getString("facebook"));
                } else {
                    findViewById(R.id.ic_facebook).setVisibility(View.GONE);
                    findViewById(R.id.tv_facebook).setVisibility(View.GONE);
                    tvFacebook.setVisibility(View.GONE);
                }
                // Twitter
                if(json.getString("twitter") != "null") {
                    tvTwitter.setText(json.getString("twitter"));
                } else {
                    findViewById(R.id.ic_twitter).setVisibility(View.GONE);
                    findViewById(R.id.tv_twitter).setVisibility(View.GONE);
                    tvTwitter.setVisibility(View.GONE);
                }
                // Google +
                if(json.getString("googlePlus") != "null") {
                    tvGooglePlus.setText(json.getString("googlePlus"));
                } else {
                    findViewById(R.id.ic_googleplus).setVisibility(View.GONE);
                    findViewById(R.id.tv_googleplus).setVisibility(View.GONE);
                    tvGooglePlus.setVisibility(View.GONE);
                }
                // Control tarjeta Redes Sociales
                if(json.getString("facebook").equals("null") && json.getString("twitter").equals("null") && json.getString("googlePlus").equals("null"))
                    findViewById(R.id.card_social).setVisibility(View.GONE);
                // Languages
                if(json.getString("language") != "null") {
                    tvLang.setText(json.getString("language"));
                    tvLangLevel.setText(json.getString("level"));
                } else {
                    findViewById(R.id.card_idiomas).setVisibility(View.GONE);
                }
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

    private class GetUserDetailsByIdTask extends AsyncTask<ApiConnector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread
            if(!perfilPublic){
                SharedPreferences prefs = getSharedPreferences(prefsUser, MODE_PRIVATE);
                userId = prefs.getInt("id", 0);
            }
            return params[0].GetUserDetailsById(userId);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            setView(jsonArray);
        }
    }

    public class LoadImageFromURL extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                InputStream is = url.openConnection().getInputStream();
                Bitmap bitMap = BitmapFactory.decodeStream(is);
                return bitMap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            ivImage.setImageBitmap(result);
        }

    }
}
