package com.digipay.digipay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.andexert.library.RippleView;

public class inventory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        // Getting the back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final RippleView rippleview = (RippleView) findViewById(R.id.add_layout);

        //waiting for ripple animation to finish
        rippleview.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener(){

            @Override
            public void onComplete(RippleView rippleView) {
                rippleview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Starting the Addinv activity
                      //  Intent add_activity = new Intent(inventory.this,Addinv.class);
                        //startActivity(add_activity);
                    }
                });

            }
        });

// Getting the add button layout


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
