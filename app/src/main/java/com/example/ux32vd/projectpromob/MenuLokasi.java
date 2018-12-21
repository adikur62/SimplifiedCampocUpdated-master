package com.example.ux32vd.projectpromob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.ByteArrayOutputStream;

import helper.Model;
import helper.ViewHolder;

public class MenuLokasi extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

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

    //mencari data dengan parameter lokasi
    private void firebaseSearch (String searchText) {
        Query firebaseSearchQuery = mRef.orderByChild("Lokasi").startAt(searchText).endAt(searchText + "\uf8ff");


        FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Model, ViewHolder>(
                        Model.class,
                        R.layout.row_layout,
                        ViewHolder.class,
                        firebaseSearchQuery
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Model model, int position) {
                        viewHolder.setDetails(getApplicationContext(), model.getLokasi(), model.getFoto(), model.getDeskripsi(), model.getDetail());

                    }

                    //bagian click pada menu lokasi
                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //Views
                                TextView mDetailDeskripsi = view.findViewById(R.id.textviewDeskripsi);
                                TextView mDetailLokasi = view.findViewById(R.id.textviewLokasi);
                                TextView mDetailDetail= view.findViewById(R.id.textviewDetail);
                                ImageView mDetailFoto = view.findViewById(R.id.imageviewFoto);
                                //ambil data dari views
                                String mDeskripsi = mDetailDeskripsi.getText().toString();
                                String mLokasi = mDetailLokasi.getText().toString();
                                String mDetail= mDetailDetail.getText().toString();
                                Drawable mDrawable = mDetailFoto.getDrawable();
                                Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

                                //lempar data ke activity baru
                                Intent intent = new Intent(view.getContext(), MenuDetailLokasi.class);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] bytes = stream.toByteArray();
                                intent.putExtra("Foto", bytes); //ubah bitmap menjadi array byte
                                intent.putExtra("Deskripsi", mDeskripsi);
                                intent.putExtra("Lokasi", mLokasi);
                                intent.putExtra("Detail", mDetail);
                                startActivity(intent); //jalankan activity detail


                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                //TODO implementasi sendiri untuk long click

                            }
                        });

                        return viewHolder;
                    }
                };

        //set adapter ke bentuk recycler view
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
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
                        Log.e("desk", "tes:"+model.getDeskripsi());
                        Log.e("detail", "tes1:"+model.getDetail());
                        viewHolder.setDetails(getApplicationContext(), model.getLokasi(), model.getFoto(), model.getDeskripsi(), model.getDetail());

                    }

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //Views
                                TextView mDetailDeskripsi = view.findViewById(R.id.textviewDeskripsi);
                                TextView mDetailLokasi = view.findViewById(R.id.textviewLokasi);
                                TextView mDetailDetail= view.findViewById(R.id.textviewDetail);
                                ImageView mDetailFoto = view.findViewById(R.id.imageviewFoto);
                                //ambil data dari views
                                String mDeskripsi = mDetailDeskripsi.getText().toString();
                                String mLokasi = mDetailLokasi.getText().toString();
                                String mDetail= mDetailDetail.getText().toString();
                                Drawable mDrawable = mDetailFoto.getDrawable();
                                Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

                                //lempar data ke activity baru
                                Intent intent = new Intent(view.getContext(), MenuDetailLokasi.class);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] bytes = stream.toByteArray();
                                intent.putExtra("Foto", bytes); //ubah bitmap menjadi array byte
                                intent.putExtra("Deskripsi", mDeskripsi);
                                intent.putExtra("Lokasi", mLokasi);
                                intent.putExtra("Detail", mDetail);
                                startActivity(intent); //jalankan activity detail


                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                //TODO implementasi sendiri untuk long click

                            }
                        });

                        return viewHolder;
                    }

                };

        //set adapter ke bentuk recycler view
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);




    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        //inflate the menu; this adds items to the action bar if it present
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //filter sesuai inputan pengguna
                firebaseSearch(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
        //inflate
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //handle other action bar item clicks here
        if (id == R.id.action_settings){
            //TODO
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //kembali ke activity sebelumnya
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
