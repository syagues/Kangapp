package projecte.kangapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import projecte.animations.Techniques;
import projecte.animations.YoYo;

/**
 * Created by sergi on 15/5/15.
 */
public class PresentacioActivity extends Activity {

    // Log
    protected static final String TAG = "PresentacioActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacio);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                YoYo.with(Techniques.FadeOutUp)
                        .duration(700)
                        .playOn(findViewById(R.id.imageView));

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        iniciarApp();
                    }
                }, 1000);
            }
        }, 2000);
    }

    public void iniciarApp() {
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }
}
