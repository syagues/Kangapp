package projecte.kangapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

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

import java.util.List;

import projecte.kangapp.adapter.CardArticulo;

/**
 * Created by sergi on 27/5/15.
 */
public class AyudaActivity extends AppCompatActivity {

    // Log
    protected static final String TAG = "AyudaActivity";

    // Toolbar
    Bundle savedInstanceState = null;
    Toolbar toolbar;

    // Preferencies
    String prefsUser = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_ayuda);

        // Toolbar (Menu lateral)
        setupToolbar();

        // Llista
        ListView llista = (ListView)findViewById(R.id.lista_sugerencias);
        final String[] opcions = {getResources().getString(R.string.str_siguenos_twitter), getResources().getString(R.string.str_visita_blog), getResources().getString(R.string.str_terminos_y_condiciones), ""};
        llista.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, opcions));
        llista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    case 0:
                        startTwitter(getApplicationContext());
                        break;
                    case 1:
                        Intent blogIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.kangapp.com/es/"));
                        startActivity(blogIntent);
                        break;
                    case 2:
                        Intent termsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://kangapp.com/terms"));
                        startActivity(termsIntent);
                        break;
                    default:
                        break;
                }
            }
        });
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
                        new PrimaryDrawerItem().withName(R.string.str_como_kanger).withIcon(R.drawable.ic_local_mall_grey600_36dp).withIdentifier(6).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.str_como_arrend).withIcon(R.drawable.ic_shopping_cart_grey600_36dp).withIdentifier(7).withCheckable(false),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.str_perfil).withIcon(R.drawable.ic_person_grey600_36dp).withIdentifier(8).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.str_ajustes).withIcon(R.drawable.ic_settings_grey600_36dp).withIdentifier(9).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.str_ayuda).withIcon(R.drawable.ic_help_orange_36dp).withIdentifier(10).withCheckable(false),
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
                                case 8:
                                    intent = new Intent(getApplicationContext(), PerfilActivity.class);
                                    break;
                                case 9:
                                    intent = new Intent(getApplicationContext(), AjustesActivity.class);
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
            result.setSelectionByIdentifier(10, false);
        }
    }

    public void startTwitter(Context context) {

        Intent intent = null;
        try {
            // get the Twitter app if possible
            context.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=Kangapp"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Kangapp"));
        }
        startActivity(intent);
    }
}

