package com.example.ux32vd.projectpromob;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import helper.Config;
import helper.Controller;
import helper.Model;
import helper.SQLiteHandler;
import helper.SessionManager;

public class EditProfile extends AppCompatActivity {

    private static final String TAG = EditProfile.class.getSimpleName();
    private Button btnEditProfile;
    private EditText editName, editEmail, editPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btnEditProfile = (Button) findViewById(R.id.btnEditProfile);
        editName = (EditText) findViewById(R.id.editName);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        editName.setText(name);
        editEmail.setText(email);

        // Register button click event
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                // Fetching user details from sqlite
                HashMap<String, String> user = db.getUserDetails();

                String uid = user.get("uid");

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    editUser(uid, name, email, password);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Silahkan masukkan data dengan lengkap", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

    }

    //request json saat melakukan registrasi user
    private void editUser(final String uid, final String name, final String email, final String password) {

        String tag_string_req = "req_edit";

        pDialog.setMessage("Menyimpan perubahan ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Config.URL_UPDATE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Update Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // user telah berhasil disimpan di MySQL
                        // menyimpan user di SQLIte
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
//                        String uid = user.getString("uid");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String updated_at = user.getString("updated_at");

                        // menambah row di tabel users
                        db.updateUser(uid, name, email, updated_at);

                        Toast.makeText(getApplicationContext(), "User berhasil diupdate!", Toast.LENGTH_LONG).show();

                        // menjalankan Menu Login
                        Intent intent = new Intent(
                                EditProfile.this,
                                MenuUtama.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Pesan error jika registrasi gagal
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            //respon error pada volley library
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // memposting parameter register ke url
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", uid);
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // menambah request ke antrian request
        Controller.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
