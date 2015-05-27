package projecte.kangapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import projecte.kangapp.adapter.DatePickerFragment;
import projecte.kangapp.adapter.RoundImage;

/**
 * Created by sergi on 26/5/15.
 */
public class AlquilarActivity extends AppCompatActivity {

    CardView cvFechaInicio, cvFechaFin;
    TextView tvFechaInicio, tvFechaFin;
    int diaActual, mesActual, anyActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alquilar);

        // Toolbar
        setupBackButton();

        // Round Image
        ImageView mItemImageView = (ImageView)findViewById(R.id.alq_iv_articulo);
        RoundImage roundedItemImage = new RoundImage(BitmapFactory.decodeResource(getResources(), R.drawable.item1));
        mItemImageView.setImageDrawable(roundedItemImage);

        ImageView mUserImageView = (ImageView)findViewById(R.id.alq_iv_usuario);
        RoundImage roundedUserImage = new RoundImage(BitmapFactory.decodeResource(getResources(), R.drawable.user1));
        mUserImageView.setImageDrawable(roundedUserImage);

        // Inicialitzem les dates amb la data actual
        tvFechaInicio = (TextView) findViewById(R.id.alq_tv_fechaini);
        tvFechaFin = (TextView) findViewById(R.id.alq_tv_fechafin);
        Calendar calendar = Calendar.getInstance();
        diaActual = calendar.get(Calendar.DAY_OF_MONTH);
        mesActual = calendar.get(Calendar.MONTH)+1;
        anyActual = calendar.get(Calendar.YEAR);
        tvFechaInicio.setText(diaActual + "/" + mesActual + "/" + Integer.toString(anyActual).substring(2, 4));
        tvFechaFin.setText(diaActual + "/" + mesActual + "/" + Integer.toString(anyActual).substring(2, 4));

        cvFechaInicio = (CardView) findViewById(R.id.alq_card_fechaini);
        cvFechaFin = (CardView) findViewById(R.id.alq_card_fechafin);
        cvFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerInicio();
            }
        });
        cvFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerFin();
            }
        });
    }

    private void showDatePickerInicio() {
        DatePickerFragment date = new DatePickerFragment();
        Bundle args = new Bundle();
        String[] fechaInicioSelec = tvFechaInicio.getText().toString().split("/");
        args.putInt("year", Integer.parseInt("20" + fechaInicioSelec[2]));
        args.putInt("month", Integer.parseInt(fechaInicioSelec[1])-1);
        args.putInt("day", Integer.parseInt(fechaInicioSelec[0]));
        date.setArguments(args);
        date.setCallBack(onDateInicio);
        date.show(getSupportFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener onDateInicio = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int month = monthOfYear + 1;
            if(year < anyActual || month < mesActual && year <= anyActual || dayOfMonth < diaActual && month <= mesActual && year <= anyActual){

            } else {
                tvFechaInicio.setText(dayOfMonth + "/" + month + "/" + Integer.toString(year).substring(2,4));
            }
        }
    };

    private void showDatePickerFin(){
        DatePickerFragment date = new DatePickerFragment();
        Bundle args = new Bundle();
        String[] fechaFinSelec = tvFechaFin.getText().toString().split("/");
        args.putInt("year", Integer.parseInt("20" + fechaFinSelec[2]));
        args.putInt("month", Integer.parseInt(fechaFinSelec[1])-1);
        args.putInt("day", Integer.parseInt(fechaFinSelec[0]));
        date.setArguments(args);
        date.setCallBack(onDateFin);
        date.show(getSupportFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener onDateFin = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int month = monthOfYear + 1;
            if(year < anyActual || month < mesActual && year <= anyActual || dayOfMonth < diaActual && month <= mesActual && year <= anyActual){

            } else {
                tvFechaFin.setText(dayOfMonth + "/" + month + "/" + Integer.toString(year).substring(2,4));
            }
        }
    };

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
