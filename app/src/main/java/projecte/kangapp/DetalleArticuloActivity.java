package projecte.kangapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewPropertyAnimator;

import projecte.kangapp.adapter.RoundImage;

/**
 * Created by sergi on 25/5/15.
 */
public class DetalleArticuloActivity extends AppCompatActivity {

    private View mFab;
    private boolean mFabIsShown;

    int drawableId;
    String nombreArticulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_articulo);

        // Round Image
        ImageView mUserImageView = (ImageView)findViewById(R.id.iv_usuario);
        RoundImage roundedImage = new RoundImage(BitmapFactory.decodeResource(getResources(), R.drawable.user1));
        mUserImageView.setImageDrawable(roundedImage);

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditarPerfilActivity.class);
                startActivity(intent);
            }
        });
        showFab();

        TextView tvName = (TextView) findViewById(R.id.name);
        tvName.setText("Nombre");

        if(this.getIntent().getExtras() != null){
            Bundle bundle = this.getIntent().getExtras();
            drawableId = bundle.getInt("drawable_id");
            nombreArticulo = bundle.getString("nombre_articulo");

            ImageView image = (ImageView) findViewById(R.id.image);
            image.setImageDrawable(getResources().getDrawable(drawableId));
            TextView name = (TextView) findViewById(R.id.name);
            name.setText(nombreArticulo);
        }
    }

    private void showFab() {
        if (!mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(1).scaleY(1).setDuration(200).start();
            mFabIsShown = true;
        }
    }
}
