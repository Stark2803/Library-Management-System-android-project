package com.example.libmansys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import java.util.Arrays;

public class Student_Profile_Activity extends Activity {
    SharedPreferences sharedpreferences;
    DatabaseHelper myDb;
    public static final String mypreference = "mypref";


    String[] languages={"Unix System Programming by Richard Stevens","Compiler Design by Charles N.Fischer","Web Technologies by Robert W.Sebesta","Computer Graphics and visualization by Edward Angel","Mobile Adhoc Networks by C.Sivaram Murthy","Management and Enterprenuership by T.Krishna Rao"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        if(sharedpreferences.getString("userType",null).equalsIgnoreCase("student"))
        Toast.makeText(getApplicationContext(),sharedpreferences.getString("userType",null),Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_student_profile);
        myDb = new DatabaseHelper(this);
        EditText dept = (EditText) findViewById(R.id.department);
        dept.setText(sharedpreferences.getString("dept",null));
        dept.setEnabled(false);


        ArrayAdapter<String> adapter = new
                ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,languages);
        AutoCompleteTextView bookToSearch=(AutoCompleteTextView)findViewById(R.id.bookName);
        adapter.setNotifyOnChange(true);
        bookToSearch.setThreshold(1);
        bookToSearch.setAdapter(adapter);


    }

    public  void AddData(View view) {

        EditText bookName = (EditText) findViewById(R.id.bookName);
        boolean status = false;
        if( Arrays.asList(languages).contains(bookName.getText().toString().trim())) {
            boolean isInserted = myDb.insertData(sharedpreferences.getString("name", null), sharedpreferences.getString("dept", null), bookName.getText().toString(),sharedpreferences.getString("regn", null));
            if (isInserted == true)
                Toast.makeText(Student_Profile_Activity.this, "Book requisition sent successfully", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(Student_Profile_Activity.this, "Book requisition failed", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(Student_Profile_Activity.this, "Book you are searching doesn't exist", Toast.LENGTH_LONG).show();
        }

    }

    public void signOut(View view)
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if(sharedpreferences.contains("userType"))
        {
            editor.clear();
            editor.commit();
        }
        Intent i = new Intent(this,Login_activity.class);
        startActivity(i);
    }

}
