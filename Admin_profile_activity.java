package com.example.libmansys;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Admin_profile_activity extends Activity {
    SharedPreferences sharedpreferences;
    DatabaseHelper myDb;
    
    
    public static final String mypreference = "mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        Toast.makeText(getApplicationContext(),sharedpreferences.getString("userType",null),Toast.LENGTH_LONG).show();
        //setContentView(R.layout.activity_admin_profile);
        myDb = new DatabaseHelper(this);
        viewAll();
    }



    public void viewAll() {
                         Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Name :"+ res.getString(1)+"\n");
                            buffer.append("USN :"+ res.getString(4)+"\n");
                            buffer.append("Department :"+ res.getString(2)+"\n");
                            buffer.append("Book Name :"+ res.getString(3)+"\n");
                            
                         
                        }

                        // Show all data
                        showMessage("Data",buffer.toString());

    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
        
    }
    
    
}
