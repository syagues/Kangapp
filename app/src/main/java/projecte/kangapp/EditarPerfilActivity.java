package projecte.kangapp;

import android.app.DatePickerDialog.OnDateSetListener;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                if(hasFocus) {
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
            int month = monthOfYear + 1;
            edNacimiento.setText(dayOfMonth + "/" + month + "/" + year);
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
}
