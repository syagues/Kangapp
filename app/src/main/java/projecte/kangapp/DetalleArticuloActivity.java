package projecte.kangapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewPropertyAnimator;

import projecte.kangapp.adapter.RoundImage;

/**
 * Created by sergi on 25/5/15.
 */
public class DetalleArticuloActivity extends AppCompatActivity {

    // Log
    protected static final String TAG = "DetalleArticuloActivity";

    private ImageButton mFabButton;

    int drawableId;
    String nombreArticulo, nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_articulo);

        // Round Image
        ImageView mUserImageView = (ImageView)findViewById(R.id.iv_usuario);
        RoundImage roundedImage = new RoundImage(BitmapFactory.decodeResource(getResources(), R.drawable.user1));
        mUserImageView.setImageDrawable(roundedImage);

        mFabButton = (ImageButton) findViewById(R.id.fabButton);
        mFabButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_shopping_cart_white_24dp));

        if(this.getIntent().getExtras() != null){
            Bundle bundle = this.getIntent().getExtras();
            drawableId = bundle.getInt("drawable_id");
            nombreArticulo = bundle.getString("nombre_articulo");
            nombreUsuario = bundle.getString("nombre_usuario");

            ImageView ivImage = (ImageView) findViewById(R.id.image);
            ivImage.setImageDrawable(getResources().getDrawable(drawableId));
            TextView tvItemName = (TextView) findViewById(R.id.name);
            TextView tvUserName = (TextView) findViewById(R.id.tv_nombre_usuario);
            tvItemName.setText(nombreArticulo);
            tvUserName.setText(nombreUsuario);
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
}
