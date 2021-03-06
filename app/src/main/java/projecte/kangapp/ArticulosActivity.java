package projecte.kangapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
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
import projecte.kangapp.listener.RecyclerItemClickListener;

/**
 * Created by sergi on 5/6/15.
 */
public class ArticulosActivity extends AppCompatActivity {

    // Log
    protected static final String TAG = "ArticulosActivity";

    // Preferencies
    String prefsUser = "user";

    // Toolbar
    Bundle savedInstanceState = null;
    Toolbar toolbar;

    // Items List
    List<CardArticulo> itemList;

    // Cerca
    SearchView searchView;
    boolean perMarca = false;
    boolean perTipo = false;
    boolean perCategoria = false;
    String search;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    // Preferences
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_articulos);

        // Toolbar (Menu lateral)
        setupToolbar();
        new GetItemsByLocaleTask().execute(new ApiConnector());

        // Publicar button
        ImageButton publicarButton = (ImageButton)findViewById(R.id.publicarButton);
        publicarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PublicarActivity.class);
                startActivity(intent);
            }
        });

        // Mapas button
        ImageButton mapaButton = (ImageButton)findViewById(R.id.mapaButton);
        mapaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            }
        });

        // ItemList
        itemList = new ArrayList<>();
    }

    public void setupToolbar(){
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = getSharedPreferences(prefsUser, MODE_PRIVATE);
        userId = prefs.getInt("id", 0);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_articulos, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getResources().getString(R.string.str_buscar));

        setupSearchView();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                View menuItemView = findViewById(R.id.action_settings);
                final PopupMenu popupMenu = new PopupMenu(this, menuItemView);
                popupMenu.inflate(R.menu.menu_search_filter);
                popupMenu.show();

                // Item seleccionat
                if(perMarca){
                    popupMenu.getMenu().findItem(R.id.action_por_marca).setChecked(true);
                } else if(perTipo){
                    popupMenu.getMenu().findItem(R.id.action_por_tipo).setChecked(true);
                } else if(perCategoria){
                    popupMenu.getMenu().findItem(R.id.action_por_categoria).setChecked(true);
                }
                // Listener al clicar item
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.action_por_marca:
                                perMarca = true;
                                perTipo = perCategoria = false;
                                return true;
                            case R.id.action_por_tipo:
                                perTipo = true;
                                perMarca = perCategoria = false;
                                return true;
                            case R.id.action_por_categoria:
                                perCategoria = true;
                                perMarca = perTipo = false;
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initRecyclerView() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new RecyclerAdapter(getItemList());
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
    }

    private void createItemList(JSONArray jsonArray) {
        if(jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = null;
                try {
                    json = jsonArray.getJSONObject(i);
                    itemList.add(new CardArticulo(json.getInt("item_id"), getDownloadUrl(json.getString("path")), json.getString("company") + " " + json.getString("model"), json.getString("category") + ", " + json.getString("type"), json.getString("username") + " " + json.getString("surname"), "", "", ""));
                    // Log.i(TAG, json.getInt("item_id") + ", " + getDownloadUrl(json.getString("path")));
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

    private void setupSearchView() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                itemList.clear();
                search = query;
                if(perMarca){
                    new GetItemsByCompanyNameTask().execute(new ApiConnector());
                } else if(perTipo){
                    new GetItemsByTypeNameTask().execute(new ApiConnector());
                } else if(perCategoria){
                    new GetItemsByCategoryNameTask().execute(new ApiConnector());
                } else {
                    new GetItemsByLocaleTask().execute(new ApiConnector());
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }

    private class GetItemsByLocaleTask extends AsyncTask<ApiConnector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread
            return params[0].GetItemsByLocale("es");
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            createItemList(jsonArray);
            initRecyclerView();
        }
    }

    private class GetItemsByCompanyNameTask extends AsyncTask<ApiConnector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread
            return params[0].GetItemsByCompanyName(search, "es");
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            createItemList(jsonArray);
            recyclerAdapter.notifyDataSetChanged();
        }
    }

    private class GetItemsByTypeNameTask extends AsyncTask<ApiConnector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread
            return params[0].GetItemsByTypeName(search, "es");
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            createItemList(jsonArray);
            recyclerAdapter.notifyDataSetChanged();
        }
    }

    private class GetItemsByCategoryNameTask extends AsyncTask<ApiConnector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread
            return params[0].GetItemsByCategoryName(search, "es");
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            createItemList(jsonArray);
            recyclerAdapter.notifyDataSetChanged();
        }
    }
}
