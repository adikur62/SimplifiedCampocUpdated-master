package com.example.ux32vd.projectpromob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import helper.Model;
import helper.ViewHolder;

public class MenuLokasi extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    private Button btnPeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_lokasi);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Daftar Lokasi");

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Data");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Model, ViewHolder>(
                    Model.class,
                    R.layout.row_layout,
                    ViewHolder.class,
                    mRef
                ){
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Model model, int position) {

                        viewHolder.setDetails(getApplicationContext(), model.getLokasi(), model.getFoto(), model.getDeskripsi());

                    }
                };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

        //intent ke menu peta
        btnPeta = (Button) findViewById(R.id.btnPeta);
        btnPeta.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        MenuPeta.class);
                startActivity(i);
                finish();
            }
        });
    }
}
