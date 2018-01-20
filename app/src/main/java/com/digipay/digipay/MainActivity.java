package com.digipay.digipay;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static MainActivity inst;
    NavigationView nav;
    ArrayList<String> sms_list = new ArrayList<String>();
    ListView sms_listview;
    ArrayAdapter arrayadpt;
    private DrawerLayout mdrawer;
    private ActionBarDrawerToggle toggle;

    public static MainActivity instance(){
        return inst;
    }

    @Override
    protected void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences("prefs",0);
        boolean firstrun = settings.getBoolean("firstrun",true);
        if(firstrun){
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstrun", false);
            editor.apply();
            startActivityForResult(new Intent(this, setup_page.class),010);
        }


        mdrawer = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,mdrawer,R.string.open,R.string.close);

        mdrawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        nav = (NavigationView) findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.nav_sell:
                        Intent s = new Intent(MainActivity.this,sell_item.class);
                        startActivity(s);
                        break;
                    case R.id.nav_inventory:
                        Intent i = new Intent(MainActivity.this,inventory.class);
                        startActivity(i);
                        break;
                    case R.id.nav_report:
                        Intent r = new Intent(MainActivity.this,reports.class);
                        startActivity(r);
                        break;
                    case R.id.nav_sms:
                        mdrawer.closeDrawers();
                }

                return false;
            }
        });


        sms_listview = (ListView) findViewById(R.id.smslist);
        arrayadpt = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,sms_list);
        sms_listview.setAdapter(arrayadpt);
        sms_listview.setOnItemClickListener(this);
        refresh_smsinbox ();


    }

    public void refresh_smsinbox(){
        ContentResolver contentResolver = getContentResolver();
        Cursor sms_cursor = contentResolver.query(Uri.parse("content://sms/inbox"),null,"address='MobileMoney'",null, null);
        int index_body = sms_cursor.getColumnIndex("body");
        int index_address = sms_cursor.getColumnIndex("address");

      //  String raw_date_string = sms_cursor.getString(raw_date);


        // Long timestamp = Long.parseLong(raw_date);
       // Date date = new Date (raw_date);
        //SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        //String date_in_string =format.format(date) ;

        if (index_body < 0 || !sms_cursor.moveToFirst())return;
            arrayadpt.clear();
            do {
                Long raw_date= Long.valueOf(sms_cursor.getString(sms_cursor.getColumnIndex("date")));
                Date date = new Date (raw_date);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
                String date_in_string =format.format(date);

                String string = "SMS From: "+ sms_cursor.getString(index_address)+"\n"+"Date: " + date_in_string+ "\n\n"+ sms_cursor.getString(index_body) + "\n";
                arrayadpt.add(string);
                arrayadpt.notifyDataSetChanged();

            }while(sms_cursor.moveToNext());

    }

    public void updateList (final String smsmsg){
        arrayadpt.insert(smsmsg,0);
        arrayadpt.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        try {

            String[] smsmessages = sms_list.get(position).split("\n");
            String address = smsmessages[0];
            String smsmessage = "";

            for (int i=1; i < smsmessages.length;i++){
                smsmessage += smsmessages[i];
            }

            String smsMessage_ = address + "\n";
            smsMessage_+=smsmessage;
            Toast.makeText(this,"Processed \n Transaction Stored",Toast.LENGTH_LONG).show();


        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
