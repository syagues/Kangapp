package projecte.kangapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
 * Created by RAÜL on 18/05/2015.
 */

public class DadesActivity extends Activity  {


    boolean eNom=false;
    boolean eCognom=false;
    boolean eContras=false;
    boolean eNick=false;
    boolean eCorreu=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dades);



        final Button benvDades = (Button) findViewById(R.id.bEnviarDades);
        final EditText etNom = (EditText) findViewById(R.id.etNomDades);
        final EditText etContrasenya = (EditText) findViewById(R.id.etContrasenyaDades);
        etContrasenya.setTypeface(Typeface.DEFAULT);
        etContrasenya.setTransformationMethod(new PasswordTransformationMethod());
        final EditText etCorreu = (EditText) findViewById(R.id.etCorreuElectronicDades);
        final EditText etConfCont = (EditText) findViewById(R.id.etConfirmaContrasenya);
        etConfCont.setTypeface(Typeface.DEFAULT);
        etConfCont.setTransformationMethod(new PasswordTransformationMethod());
        final EditText etNick = (EditText) findViewById(R.id.etNick);
        final EditText etCognoms = (EditText) findViewById(R.id.etApellido);



        etNom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (etNom.getText().toString().length() != 0) {
                    eNom = true;
                }
                else eNom = false;
                if (eNom && eCognom && eContras && eCorreu && eNick){
                    benvDades.setVisibility(View.VISIBLE);
                }
                else benvDades.setVisibility(View.INVISIBLE);
            }
        });

        etCognoms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etCognoms.getText().toString().length()!=0){
                    eCognom=true;
                }
                else eCognom = false;
                if (eNom && eCognom && eContras && eCorreu && eNick){
                    benvDades.setVisibility(View.VISIBLE);
                }
                else benvDades.setVisibility(View.INVISIBLE);
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
                if (etContrasenya.getText().toString().length()!=0 && etContrasenya.getText().toString().equals(etConfCont.getText().toString())){
                    eContras=true;
                }
                else eContras=false;
                if (eNom && eCognom && eContras && eCorreu && eNick){
                    benvDades.setVisibility(View.VISIBLE);
                }
                else benvDades.setVisibility(View.INVISIBLE);
            }
        });
        etCorreu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (etCorreu.getText().toString().length()!=0){
                    eCorreu=true;
                    etCorreu.setBackgroundColor(getResources().getColor(android.R.color.white));
                }
                else eCorreu = false;
                if (eNom && eCognom && eContras && eCorreu && eNick){
                    benvDades.setVisibility(View.VISIBLE);
                }
                else benvDades.setVisibility(View.INVISIBLE);
            }
        });
        etNick.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etNick.getText().toString().length()!=0){
                    etNick.setBackgroundColor(getResources().getColor(android.R.color.white));
                    eNick=true;
                }
                else eNick=false;
                if (eNom && eCognom && eContras && eCorreu && eNick){
                    benvDades.setVisibility(View.VISIBLE);

                }
                else{
                    benvDades.setVisibility(View.INVISIBLE);

                }
            }
        });
        etConfCont.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etContrasenya.getText().toString().length()!=0 && etContrasenya.getText().toString().equals(etConfCont.getText().toString())){
                    eContras=true;
                }
                else eContras = false;
                if (eNom && eCognom && eContras && eCorreu && eNick){
                    benvDades.setVisibility(View.VISIBLE);
                }
                else {
                    benvDades.setVisibility(View.INVISIBLE);
                }
            }
        });

        benvDades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidEmail(etCorreu.getText().toString())){
                    YoYo.with(Techniques.Shake).duration(500).playOn(findViewById(R.id.etCorreuElectronicDades));
                    Toast toast = Toast.makeText(getApplicationContext(), "EL MAIL INTRODUICIDO NO ES CORRECTO", Toast.LENGTH_LONG);
                    LinearLayout linearLayout = (LinearLayout) toast.getView();
                    TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                    messageTextView.setTextSize(12);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etCorreu.setBackground(getResources().getDrawable(R.drawable.validacioincorrecta));
                } else {
                    if (etNick.getText().toString().equals("raul")) {
                        Toast tolast = Toast.makeText(getApplicationContext(), "EL NICK ELEGIDO YA HA SIDO ELEGIDO POR OTRO USUARIO", Toast.LENGTH_LONG);
                        LinearLayout linearLayout = (LinearLayout) tolast.getView();
                        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                        messageTextView.setTextSize(12);
                        tolast.setGravity(Gravity.CENTER,0,0);
                        tolast.show();
                        YoYo.with(Techniques.Shake).duration(500).playOn(findViewById(R.id.etNick));
                        etNick.setBackground(getResources().getDrawable(R.drawable.validacioincorrecta));
                    }
                    else{
                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(Intent.EXTRA_EMAIL,etCorreu.getText().toString());
                        email.putExtra(Intent.EXTRA_SUBJECT, "Bienvenido " + etNick.getText().toString());
                        email.putExtra(Intent.EXTRA_TEXT, "Hola " + etNick.getText().toString() + "!!\bPara completar la validación de la cuenta visita el link.");
                        email.setType("message/rfc822");
                        startActivity(Intent.createChooser(email, "Tria: "));
                    }

                }
            }
        });


    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


}