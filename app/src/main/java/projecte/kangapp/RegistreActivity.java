package projecte.kangapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import projecte.animations.Techniques;
import projecte.animations.YoYo;

/**
 * Created by RAÃœL on 18/05/2015.
 */
public class RegistreActivity extends Activity {

    boolean cEnt = false;
    boolean nEnt = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);

        final Button bregistre = (Button) findViewById(R.id.bRegistre);
        final Button bacces = (Button) findViewById(R.id.bAcces);
        final EditText etNom = (EditText) findViewById(R.id.etNom);
        final EditText etContrasenya = (EditText) findViewById(R.id.etContrasenya);
        etContrasenya.setTypeface(Typeface.DEFAULT);
        etContrasenya.setTransformationMethod(new PasswordTransformationMethod());


        etNom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nEnt=true;
                if (cEnt==true){
                    bacces.setEnabled(true);
                }
            }
        });

        etContrasenya.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cEnt=true;
                if (nEnt==true){
                    bacces.setEnabled(true);
                }
            }
        });

        bregistre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDades = new Intent(getApplicationContext(), DadesActivity.class);
                startActivity(iDades);
                finish();


            }

        });

        bacces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etNom.getText().toString().equals("Raul")) {
                    Intent iPrincipal = new Intent(getApplicationContext(), PrincipalActivity.class);
                    startActivity(iPrincipal);
                    finish();
                } else {
                    cEnt=false;
                    nEnt=false;
                    bacces.setEnabled(true);
                    YoYo.with(Techniques.Shake).duration(500).playOn(findViewById(R.id.etNom));
                    YoYo.with(Techniques.Shake).duration(500).playOn(findViewById(R.id.etContrasenya));
                    etContrasenya.getText().clear();
                    etContrasenya.getText().clear();
                    Toast toast = Toast.makeText(getApplicationContext(), "EL USUARIO Y LA CONTRASEÑA NO COINCIDEN", Toast.LENGTH_LONG);
                    LinearLayout linearLayout = (LinearLayout) toast.getView();
                    TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                    messageTextView.setTextSize(12);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }

            }
        });

    }

}
