package projecte.kangapp;

import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import projecte.kangapp.adapter.RoundImage;

/**
 * Created by sergi on 26/5/15.
 */
public class AlquilarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alquilar);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary)));

        // Round Image
        ImageView mItemImageView = (ImageView)findViewById(R.id.alq_iv_articulo);
        RoundImage roundedItemImage = new RoundImage(BitmapFactory.decodeResource(getResources(), R.drawable.item1));
        mItemImageView.setImageDrawable(roundedItemImage);

        ImageView mUserImageView = (ImageView)findViewById(R.id.alq_iv_usuario);
        RoundImage roundedUserImage = new RoundImage(BitmapFactory.decodeResource(getResources(), R.drawable.user1));
        mUserImageView.setImageDrawable(roundedUserImage);
    }
}
