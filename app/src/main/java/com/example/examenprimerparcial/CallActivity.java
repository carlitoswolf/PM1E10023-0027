package com.example.examenprimerparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CallActivity extends AppCompatActivity {
    EditText numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        numero = (EditText) findViewById(R.id.txtnumero);
//        Intent intent = getIntent();
//        String d1 = intent.getStringExtra("dato");
        MainActivity getPhone =  new MainActivity();
        String getPhoneNumber = "+504" + getPhone.pasar;
        numero.setText(getPhoneNumber);
        Button btnAtras = (Button) findViewById(R.id.btnAtras);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}