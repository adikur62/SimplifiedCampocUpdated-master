package com.example.ux32vd.projectpromob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import helper.SQLiteHandler;
import helper.SessionManager;

public class MenuUtama extends AppCompatActivity {

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout, btnEditProfile, btnMulai;
    private Button btnPeta;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);

        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnEditProfile = (Button) findViewById(R.id.btnEditProfile);
        btnMulai = (Button) findViewById(R.id.btnMulai);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        //intent to edit profile & mulai
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),EditProfile.class);
                startActivity(intent);
//                finish();

            }
        });

        btnMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),MenuLokasi.class);
                startActivity(intent);
//                finish();

            }
        });

        //intent ke menu peta
        btnPeta = (Button) findViewById(R.id.btnPeta);
        btnPeta.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        MenuPeta.class);
                startActivity(i);
//                finish();
            }
        });
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MenuUtama.this, LoginScreen.class);
        startActivity(intent);
        finish();
    }

}
