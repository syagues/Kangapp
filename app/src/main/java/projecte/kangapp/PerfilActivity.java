package projecte.kangapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.nineoldandroids.view.ViewHelper;

import scrolls.ObservableScrollView;
import scrolls.ObservableScrollViewCallbacks;
import scrolls.ScrollState;
import scrolls.ScrollUtils;

/**
 * Created by sergi on 20/5/15.
 */
public class PerfilActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    private View mImageView;
    private View mToolbarView;
    private ObservableScrollView mScrollView;
    private int mParallaxImageHeight;

    // Toolbar
    Bundle savedInstanceState = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Toolbar (Menu lateral)
        setupToolbar();

        mImageView = findViewById(R.id.image);
        mToolbarView = findViewById(R.id.toolbar);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));

        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
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

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.primary);
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(mImageView, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
}
