package projecte.kangapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import projecte.kangapp.adapter.DatePickerFragment;
import projecte.kangapp.adapter.RoundImage;
import projecte.kangapp.database.ApiConnector;

/**
 * Created by sergi on 26/5/15.
 */
public class AlquilarActivity extends AppCompatActivity {

    // Log
    protected static final String TAG = "AlquilarActivity";

    // Preferencies
    String prefsUser = "user";

    CardView cvFechaInicio, cvFechaFin;
    TextView tvFechaInicio, tvFechaFin;
    int diaActual, mesActual, anyActual;

    int articuloId;
    ImageView ivImage, ivUser;

    String startDate, endDate, subject, status, price, priceDay, priceWeek, priceHalfMonth, priceMonth, deposit;
    int kangerId, arrenderId, idKanger, idArrender;

    TextView tvItemName, tvType, tvUserName, tvUserCountry, tvUserItems, tvUserDeals, tvUserRate, tvStartDate, tvEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alquilar);

        // Toolbar
        setupBackButton();

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

        // Parametres rebut
        if(this.getIntent().getExtras() != null){
            Bundle bundle = this.getIntent().getExtras();
            articuloId = bundle.getInt("articulo_id");
        }

        // Inicialitzem valors BBDD
        new GetItemDetailsByIdTask().execute(new ApiConnector());

        // Alquilar Button
        ImageButton alquilarButton = (ImageButton) findViewById(R.id.alquilarButton);
        alquilarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alquila();
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
                showAlertDialog("anterior");
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
                showAlertDialog("anterior");
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

    public void setView(JSONArray jsonArray) {
        ivImage = (ImageView) findViewById(R.id.alq_iv_articulo);
        ivUser = (ImageView) findViewById(R.id.alq_iv_usuario);
        tvItemName = (TextView) findViewById(R.id.alq_tv_nombre_articulo);
        tvType = (TextView) findViewById(R.id.alq_tv_tipo_articulo);
        tvUserName = (TextView) findViewById(R.id.alq_tv_nombre_usuario);
        tvUserCountry = (TextView) findViewById(R.id.alq_tv_pais_usuario);
        tvUserItems = (TextView) findViewById(R.id.alq_tv_user_articulos);
        tvUserDeals = (TextView) findViewById(R.id.alq_tv_user_tratos);
        tvUserRate = (TextView) findViewById(R.id.alq_tv_user_puntuacion);

        JSONObject json = null;
        try {
            json = jsonArray.getJSONObject(0);
            // Imatge item
            LoadItemImageFromURL loadItemImage = new LoadItemImageFromURL();
            if(getDownloadUrl(json.getString("path_item")) != null)
                loadItemImage.execute(getDownloadUrl(json.getString("path_item")));
            // Imatge user
            LoadUserImageFromURL loadUserImage = new LoadUserImageFromURL();
            if(getDownloadUrl(json.getString("path_user")) != null)
                loadUserImage.execute(getDownloadUrl(json.getString("path_user")));
            tvItemName.setText(json.getString("company") + " " + json.getString("model"));
            tvType.setText(json.getString("category") + ", " + json.getString("type"));
            tvUserName.setText(json.getString("username") + " " + json.getString("surname"));
            tvUserCountry.setText(json.getString("geo"));
            tvUserItems.setText(Integer.toString(json.getInt("items")));
            tvUserDeals.setText(Integer.toString(json.getInt("deals")));
            if(!json.getString("rate").equals("null"))
                tvUserRate.setText(Float.toString((float) json.getDouble("rate") / 2));

            // Inicialitzem els valors necessaris per crear un nou tracte
            priceDay = json.getString("price_day");
            priceWeek = json.getString("price_week");
            priceHalfMonth = json.getString("price_halfmonth");
            priceMonth = json.getString("price_month");
            deposit = json.getString("deposit");
            kangerId = json.getInt("userid");
            idKanger = json.getInt("iduser");
            new GetUserIdByIdTask().execute(new ApiConnector());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void alquila(){

        boolean totCorrecte = true;
        tvStartDate = (TextView) findViewById(R.id.alq_tv_fechaini);
        tvEndDate = (TextView) findViewById(R.id.alq_tv_fechafin);

        // Dates
        String[] aStDt = tvStartDate.getText().toString().split("/");
        int diaSt = Integer.parseInt(aStDt[0]);
        int mesSt = Integer.parseInt(aStDt[1]);
        int anySt = Integer.parseInt(aStDt[2]);
        startDate = "20" + anySt + "-" + mesSt + "-" + diaSt;
        String[] aEndDt = tvEndDate.getText().toString().split("/");
        int diaEnd = Integer.parseInt(aEndDt[0]);
        int mesEnd = Integer.parseInt(aEndDt[1]);
        int anyEnd = Integer.parseInt(aEndDt[2]);
        endDate = "20" + anyEnd + "-" + mesEnd + "-" + diaEnd;

        // Control dates
        int diesDif = 0;
        if(anySt == anyEnd && mesSt == mesEnd){
            diesDif = diaEnd - diaSt;
            if(diesDif < 1){
                totCorrecte = false;
            }
        } else if(anySt > anyEnd || anySt+1 < anyEnd || mesSt+1 < mesEnd || (mesSt > mesEnd && mesEnd != 1)){
            totCorrecte = false;
        } else {
            if(mesSt == 1 || mesSt == 3 || mesSt == 5 || mesSt == 7 || mesSt == 8 || mesSt == 10 || mesSt == 12){
                diesDif = diaEnd - diaSt + 31;
            } else if(mesSt == 2){
                diesDif = diaEnd - diaSt + 28;
            } else {
                diesDif = diaEnd - diaSt + 30;
            }

            if(diesDif > 31){
                totCorrecte = false;
            }
        }
        Log.i(TAG, "La diferencia de dies es: " + diesDif + ", Correcte: " + totCorrecte);

        // Si les dades son correctes realitzem el tracte, si no llançem una alerta
        if(totCorrecte){
            // Control preu
            if(diesDif >= 1 && diesDif <= 3){
                price = priceDay;
            } else if(diesDif >= 4 && diesDif <= 7){
                price = priceWeek;
            } else if(diesDif >=8 && diesDif <= 15){
                price = priceHalfMonth;
            } else if(diesDif >= 16 && diesDif <= 31){
                price = priceMonth;
            }
            subject = "Compra";
            status = "initialized";

            // Creem el nou tracte
            new InsertDealTask().execute(new ApiConnector());
        } else {
            showAlertDialog("incorrecta");
        }

    }

    public void showAlertDialog(String tipo){
        switch (tipo){
            case "incorrecta":
                new AlertDialog.Builder(this)
                        .setMessage(R.string.str_alert_fecha_incorr)
                        .setPositiveButton(R.string.str_aceptar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // No fer res
                            }
                        })
                        .show();
                break;
            case "anterior":
                new AlertDialog.Builder(this)
                        .setMessage(R.string.str_alert_fecha_anterior)
                        .setPositiveButton(R.string.str_aceptar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // No fer res
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
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

    private class GetItemDetailsByIdTask extends AsyncTask<ApiConnector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread
            return params[0].GetItemDetailsById(articuloId);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            setView(jsonArray);
        }
    }

    public class LoadItemImageFromURL extends AsyncTask<String, Void, Bitmap> {

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
            RoundImage roundedImage = new RoundImage(result);
            ivImage.setImageDrawable(roundedImage);
        }

    }

    public class LoadUserImageFromURL extends AsyncTask<String, Void, Bitmap> {

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
            RoundImage roundedImage = new RoundImage(result);
            ivUser.setImageDrawable(roundedImage);
        }

    }

    private class GetUserIdByIdTask extends AsyncTask<ApiConnector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread
            SharedPreferences prefs = getSharedPreferences(prefsUser, MODE_PRIVATE);
            arrenderId = prefs.getInt("id", 0);
            return params[0].GetUserIdById(arrenderId);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(0);
                idArrender = json.getInt("user_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class InsertDealTask extends AsyncTask<ApiConnector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            // it is executed on Background thread
//            Log.i(TAG,"ItemId: " + articuloId);
//            Log.i(TAG,"Status: " + status);
//            Log.i(TAG,"Price: " + price);
//            Log.i(TAG,"Deposit: " + deposit);
//            Log.i(TAG,"StartDate: " + startDate);
//            Log.i(TAG,"EndDate: " + endDate);
//            Log.i(TAG,"KangerId: " + kangerId);
//            Log.i(TAG,"IdKanger: " + idKanger);
//            Log.i(TAG,"ArrenderId: " + arrenderId);
//            Log.i(TAG,"IdArrender: " + idArrender);
//            Log.i(TAG,"Subject: " + subject);
            return params[0].InsertDeal(articuloId,status,price,deposit,startDate,endDate,kangerId,idKanger,arrenderId,idArrender,subject);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            Log.i(TAG,"Tracte Fet!!");
            Toast.makeText(getApplicationContext(), "Petición realizada con éxito!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }
}
