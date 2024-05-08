package com.example.tiendaropa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Vender extends AppCompatActivity {

    TextView title ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender);
        title=(TextView) findViewById(R.id.title);
        title.setText("Añade tu producto");
    }
}