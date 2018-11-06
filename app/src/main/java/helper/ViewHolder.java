package helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ux32vd.projectpromob.R;
import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder{

    View mView;

    public ViewHolder(View itemView){
        super(itemView);

        mView = itemView;
    }

    public void setDetails (Context ctx, String Lokasi, String Foto, String Deskripsi){
        TextView mLokasi = mView.findViewById(R.id.textviewLokasi);
        ImageView mFoto = mView.findViewById(R.id.imageviewFoto);
        TextView mDeskripsi = mView.findViewById(R.id.textviewDeskripsi);

        mLokasi.setText(Lokasi);
        Picasso.get().load(Foto).into(mFoto);
        mDeskripsi.setText(Deskripsi);
    }
}
