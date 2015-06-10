package projecte.kangapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import projecte.kangapp.adapter.CardArticulo;
import projecte.kangapp.adapter.RecyclerAdapter;
import projecte.kangapp.database.ApiConnector;
import projecte.kangapp.listener.HidingScrollListener;
import projecte.kangapp.listener.RecyclerItemClickListener;

/**
 * Created by sergi on 23/5/15.
 */
public class ComoKangerActivity extends AppCompatActivity {

    // Log
    protected static final String TAG = "ComoKangerActivity";

    // Toolbar
    Bundle savedInstanceState = null;
    Toolbar toolbar;

    // Items List
    List<CardArticulo> itemList;

    // Preferencies
    String prefsUser = "user";
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_como_kanger);

        // Toolbar (Menu lateral)
        setupToolbar();
        new GetItemsAsKangerByUserIdTask().execute(new ApiConnector());
    }

    public void setupToolbar(){
        // Handle Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
                        new PrimaryDrawerItem().withName(R.string.str_buscar).withIdentifier(1).withIcon(R.drawable.ic_place_grey600_36dp).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.str_mis_articulos).withIdentifier(2).withIcon(R.drawable.ic_store_mall_directory_grey600_36dp).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.str_chat).withIdentifier(3).withIcon(R.drawable.ic_chat_grey600_36dp).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.str_publicar).withIdentifier(4).withIcon(R.drawable.ic_add_circle_grey600_36dp).withCheckable(false),
                        new SectionDrawerItem().withName(R.string.str_mis_tratos).withIdentifier(5),
                        new PrimaryDrawerItem().withName(R.string.str_como_kanger).withIcon(R.drawable.ic_local_mall_orange_36dp).withIdentifier(6).withCheckable(false),
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
            result.setSelectionByIdentifier(6, false);
        }
    }

    private void initRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getItemList());
        recyclerView.setAdapter(recyclerAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getApplicationContext(), DetalleArticuloActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putInt("user_id", userId);
                        bundle.putInt("item_id", itemList.get(position - 1).getArticuloId());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                })
        );

        recyclerView.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });
    }

    private void hideViews() {
        toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    private void showViews() {
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

    private void createItemList(JSONArray jsonArray) {
        itemList = new ArrayList<>();
        if(jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = null;
                try {
                    json = jsonArray.getJSONObject(i);
                    if(!json.getString("start_date").equals("null") && !json.getString("end_date").equals("null")) {
                        String[] datetimeIni = json.getString("start_date").split(" ");
                        String[] dateIni = datetimeIni[0].split("-");
                        int diaIni = Integer.parseInt(dateIni[2]);
                        int mesIni = Integer.parseInt(dateIni[1]);
                        int anyIni = Integer.parseInt(dateIni[0]);
                        String[] datetimeEnd = json.getString("end_date").split(" ");
                        String[] dateEnd = datetimeEnd[0].split("-");
                        int diaEnd = Integer.parseInt(dateEnd[2]);
                        int mesEnd = Integer.parseInt(dateEnd[1]);
                        int anyEnd = Integer.parseInt(dateEnd[0]);
                        itemList.add(new CardArticulo(json.getInt("item_id"), getDownloadUrl(json.getString("path")), json.getString("company") + " " + json.getString("model"), json.getString("category") + ", " + json.getString("type"), json.getString("username") + " " + json.getString("surname"), json.getString("price") + " €", diaIni + "/" + mesIni + " - " + diaEnd + "/" + mesEnd, json.getString("state")));
                    } else {
                        itemList.add(new CardArticulo(json.getInt("item_id"), getDownloadUrl(json.getString("path")), json.getString("company") + " " + json.getString("model"), json.getString("category") + ", " + json.getString("type"), json.getString("username") + " " + json.getString("surname"), json.getString("price") + " €", "", json.getString("state")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<CardArticulo> getItemList() {
        return itemList;
    }

    public String getDownloadUrl(String path){
        String url;
        if(path != "null") {
            String[] pathSplit = path.split("/");
            url = "http://46.101.24.238";
            for (int i = 0; i < pathSplit.length; i++) {
                if (i > 4) {
                    url += "/" + pathSplit[i];
                }
            }
        } else {
            return null;
        }
        return url;
    }

    private class GetItemsAsKangerByUserIdTask extends AsyncTask<ApiConnector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread
            SharedPreferences prefs = getSharedPreferences(prefsUser, MODE_PRIVATE);
            userId = prefs.getInt("id", 0);
            return params[0].GetItemsAsKangerByUserId(userId,"es");
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            createItemList(jsonArray);
            initRecyclerView();
        }
    }
}
