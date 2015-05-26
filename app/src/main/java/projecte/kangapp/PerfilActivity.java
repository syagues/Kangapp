package projecte.kangapp;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
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
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

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
    int drawableId;
    String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        if(this.getIntent().getExtras() != null){
            // Toolbar (Back button)
            setupBackButton();

            Bundle bundle = this.getIntent().getExtras();
            drawableId = bundle.getInt("drawable_id");
            nombreUsuario = bundle.getString("nombre_usuario");

            ImageView ivImage = (ImageView) findViewById(R.id.image);
            ivImage.setImageDrawable(getResources().getDrawable(drawableId));
            TextView tvUserName = (TextView) findViewById(R.id.name);
            Log.i(TAG, nombreUsuario);
            tvUserName.setText(nombreUsuario);

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
            TextView tvName = (TextView) findViewById(R.id.name);
            tvName.setText("Nombre");
        }

    }

    public void setupToolbar(){
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        final IProfile profile = new ProfileDrawerItem().withName("Usuari user").withEmail("usuari@gmail.com").withIcon(getResources().getDrawable(R.drawable.user1));

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
                        new PrimaryDrawerItem().withName(R.string.str_ajustes).withIcon(R.drawable.ic_settings_grey600_36dp).withIdentifier(9).withCheckable(false)

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
                                case 6:
                                    intent = new Intent(getApplicationContext(), ComoKangerActivity.class);
                                    break;
                                case 7:
                                    intent = new Intent(getApplicationContext(), ComoArrendatarioActivity.class);
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
}
