package com.example.libmansys;

	import android.app.Activity;
import android.app.NotificationManager;
	import android.app.PendingIntent;
	import android.content.Context;
	import android.content.Intent;
	import android.content.SharedPreferences;
	import android.database.Cursor;
	import android.support.v4.app.NotificationCompat;
	import android.os.Bundle;
	import android.view.View;
	import android.widget.Button;
	import android.widget.EditText;
import android.widget.Toast;

	public class Login_activity extends Activity {
	    SharedPreferences sharedpreferences;
	    DatabaseHelper  registerUser;
	    public static final String mypreference = "mypref";
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_login);
	        sharedpreferences = getSharedPreferences(mypreference,
	                Context.MODE_PRIVATE);
	        registerUser = new DatabaseHelper(this);
	    }

	    public void login(View view)
	    {
	        EditText userName = (EditText) findViewById(R.id.userName);
	        EditText password = (EditText) findViewById(R.id.password);
	        Button signOn = (Button) findViewById(R.id.signOn);

	        String s_userName = userName.getText().toString();
	        String s_password = password.getText().toString();

	        SharedPreferences.Editor editor = sharedpreferences.edit();

	        Cursor res=registerUser.getStudent(s_userName, s_password);

	        if(res.getCount() == 1 ) {
	                editor.putString("userType","student");
	                String username = "";
	            while (res.moveToNext()) {
	            	editor.putString("regn", res.getString(0));
	                editor.putString("dept", res.getString(2));
	                username = res.getString(1);
	                editor.putString("name", username);
	            }
	                editor.commit();
	            Intent i = new Intent(this, Student_Profile_Activity.class);
	            startActivity(i);
	            Toast.makeText(Login_activity.this, "Welcome "+username, Toast.LENGTH_LONG).show();

	        }
	        else
	            if(s_userName.compareTo("admin")==0 && s_password.compareTo("admin@1234")==0) {
	                    editor.putString("userType","admin");
	                    editor.commit();

	                Intent i = new Intent(this, Admin_profile_activity.class);
	                startActivity(i);
	                addNotification();
	            }
	        else {
	                setContentView(R.layout.activity_login);
	                Toast.makeText(Login_activity.this, "Sorry, you entered wrong credentials", Toast.LENGTH_LONG).show();
	            }
	    }

	    public void signUp(View view)
	    {
	        Intent i = new Intent(this, RegisterActivity.class);
	        startActivity(i);
	    }



	    private void addNotification() {
	        NotificationCompat.Builder builder =
	                new NotificationCompat.Builder(this)
	                        .setSmallIcon(R.drawable.ic_launcher)
	                        .setContentTitle("Admin Login")
	                        .setContentText("Hi, you have logged in as ADMIN");

	        Intent notificationIntent = new Intent(this, MainActivity.class);
	        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
	                PendingIntent.FLAG_UPDATE_CURRENT);
	        builder.setContentIntent(contentIntent);


	        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	        manager.notify(0, builder.build());
	    }

	}