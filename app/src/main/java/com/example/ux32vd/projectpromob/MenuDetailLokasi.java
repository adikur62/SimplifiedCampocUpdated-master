package com.example.ux32vd.projectpromob;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MenuDetailLokasi extends AppCompatActivity {

    TextView mDetailDeskripsi, mDetailLokasi, mDetailDetail;
    ImageView mDetailFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail_lokasi);

        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        //Judul action bar
        actionBar.setTitle("Detail Lokasi");
        //tombol back di action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //penjelasan views
        mDetailDeskripsi = findViewById(R.id.txtDeskripsi);
//        mDetailLokasi = findViewById(R.id.DetailtextviewLokasi);
        mDetailDetail = findViewById(R.id.txtDetail);
        mDetailFoto = findViewById(R.id.imgDet);

        //ambil data dari intent
        byte[] bytes = getIntent().getByteArrayExtra("Foto");
        String Deskripsi = getIntent().getStringExtra("Deskripsi");
        String Lokasi = getIntent().getStringExtra("Lokasi");
        getSupportActionBar().setTitle(Lokasi);

        String Detail = getIntent().getStringExtra("Detail");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


        //set data ke views
        mDetailDeskripsi.setText(Deskripsi);
//        mDetailLokasi.setText(Lokasi);
        mDetailDetail.setText(Detail);
        mDetailFoto.setImageBitmap(bmp);

    }


    //kembali ke activity sebelumnya
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
