package projecte.kangapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewPropertyAnimator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import projecte.kangapp.adapter.RoundImage;

/**
 * Created by sergi on 25/5/15.
 */
public class DetalleArticuloActivity extends AppCompatActivity {

    // Log
    protected static final String TAG = "DetalleArticuloActivity";

    private ImageButton mFabButton;

    boolean isForRent;
    int articuloId;
    String nombreArticulo, nombreUsuario;
    ImageView ivImage, ivUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_articulo);

        if(this.getIntent().getExtras() != null){
            Bundle bundle = this.getIntent().getExtras();
            isForRent = bundle.getBoolean("is_for_rent");
            articuloId = bundle.getInt("item_id");

            Log.i(TAG, "item_id = " + Integer.toString(articuloId));
            new GetItemDetailsByIdTask().execute(new ApiConnector());

            mFabButton = (ImageButton) findViewById(R.id.fabButton);
            if(isForRent) {
                mFabButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_shopping_cart_white_24dp));
            } else {
                mFabButton.setVisibility(View.GONE);
            }
        }

        CardView usuarioCard = (CardView) findViewById(R.id.card_usuario);
        usuarioCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PerfilActivity.class);

                Bundle bundle = new Bundle();
                ImageView userImage = (ImageView) findViewById(R.id.iv_usuario);
                Log.i(TAG, userImage.getDrawable().toString());
                bundle.putInt("drawable_id", R.drawable.user1);
                Log.i(TAG, nombreUsuario);
                bundle.putString("nombre_usuario", nombreUsuario);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        ImageButton alquilarButton = (ImageButton) findViewById(R.id.fabButton);
        alquilarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AlquilarActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setView(JSONArray jsonArray) {
        ivImage = (ImageView) findViewById(R.id.image);
        ivUser = (ImageView) findViewById(R.id.iv_usuario);
        TextView tvItemName = (TextView) findViewById(R.id.name);
        TextView tvType = (TextView) findViewById(R.id.tv_tipo);
        TextView tvUserName = (TextView) findViewById(R.id.tv_nombre_usuario);
        TextView tvUserCountry = (TextView) findViewById(R.id.tv_pais_usuario);
        TextView tvPrice1 = (TextView) findViewById(R.id.tv_precio1);
        TextView tvPrice2 = (TextView) findViewById(R.id.tv_precio2);
        TextView tvPrice3 = (TextView) findViewById(R.id.tv_precio3);
        TextView tvPrice4 = (TextView) findViewById(R.id.tv_precio4);

        JSONObject json = null;
        try {
            json = jsonArray.getJSONObject(0);

            LoadItemImageFromURL loadItemImage = new LoadItemImageFromURL();
            loadItemImage.execute(getDownloadUrl(json.getString("path_item")));
            LoadUserImageFromURL loadUserImage = new LoadUserImageFromURL();
            loadUserImage.execute(getDownloadUrl(json.getString("path_user")));
            tvItemName.setText(json.getString("company") + " " + json.getString("model"));
            tvType.setText(json.getString("category") + ", " + json.getString("type"));
            tvUserName.setText(json.getString("username") + " " + json.getString("surname"));
            tvUserCountry.setText(json.getString("geo"));
            tvPrice1.setText(json.getString("price_day") + " €");
            tvPrice2.setText(json.getString("price_week") + " €");
            tvPrice3.setText(json.getString("price_halfmonth") + " €");
            tvPrice4.setText(json.getString("price_month") + " €");

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
            ivImage.setImageBitmap(result);
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
}
