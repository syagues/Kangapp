package projecte.kangapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

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

import scrolls.ObservableScrollView;
import scrolls.ObservableScrollViewCallbacks;
import scrolls.ScrollState;
import scrolls.ScrollUtils;

/**
 * Created by RA�L on 21/05/2015.
 */
public class PublicarActivity extends AppCompatActivity {

    // Log
    protected static final String TAG = "PublicarActivity";

    // Toolbar
    Bundle savedInstanceState = null;

    // Preferencies
    String prefsUser = "user";
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar);

        setupBackButton();

        String uso[] = {"Para Dormir", "Para Pasear", "Para el Coche", "Para el Baño", "Para Jugar", "Otros"};

        Spinner sp_uso = (Spinner) findViewById(R.id.sp_uso);
        ArrayAdapter<String> sp_AA_Uso = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, uso);
        sp_AA_Uso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_uso.setAdapter(sp_AA_Uso);

        String que[] = {"Silla Coche", "Bañera", "Silla Coche", "Cuna", "Parque de Juegos", "Otros"};
        Spinner sp_que = (Spinner) findViewById(R.id.sp_que);
        ArrayAdapter<String> sp_AA_Que = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, que);
        sp_AA_Que.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_que.setAdapter(sp_AA_Que);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_publicar, menu);

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
}