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

    TextView mDetailDeskripsi, mDetailLokasi, mDetail;
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
        mDetailDeskripsi = findViewById(R.id.txtDetail);
        mDetailFoto = findViewById(R.id.imgDet);

        //GET INTENT
        Intent i=this.getIntent();

        //RECEIVE DATA
        String lokasi=i.getExtras().getString("LOKASI_KEY");
        getSupportActionBar().setTitle(lokasi);
        String deskripsi=i.getExtras().getString("DESKRIPSI_KEY");
//        String foto=i.getExtras().getString("FOTO_KEY");

        //BIND DATA
        mDetailDeskripsi.setText(deskripsi);
//        Picasso.get().load(foto).into(mDetailFoto);

//        //ambil data dari intent
//        byte[] bytes = getIntent().getByteArrayExtra("Foto");
//        String Detail = getIntent().getStringExtra("Detail");
//        String Lokasi = getIntent().getStringExtra("Lokasi");
//        getSupportActionBar().setTitle(Lokasi);
//        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//
//        //set data ke views
//        mDetail.setText(Detail);
//        mDetailFoto.setImageBitmap(bmp);

    }
}
