package com.digipay.digipay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class setup_page extends AppCompatActivity {
    Button setupbtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_page);
        setupbtn = (Button) findViewById(R.id.setupbtn);

        setupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent m = new Intent(setup_page.this,MainActivity.class);
                //startActivity(m);
                finish();
            }
        });
    }
}
