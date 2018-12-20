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

        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });

        //item long click
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            }
        });
    }

    public void setDetails (Context ctx, String Lokasi, String Foto, String Deskripsi, String Detail){
        TextView mLokasi = mView.findViewById(R.id.textviewLokasi);
        ImageView mFoto = mView.findViewById(R.id.imageviewFoto);
        TextView mDeskripsi = mView.findViewById(R.id.textviewDeskripsi);
        TextView mDetail = mView.findViewById(R.id.textviewDetail);

        mLokasi.setText(Lokasi);
        Picasso.get().load(Foto).into(mFoto);
        mDeskripsi.setText(Deskripsi);
        mDetail.setText(Detail);
    }

    private ViewHolder.ClickListener mClickListener;

    //interface untuk mnegirim callback
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick (View view, int position);
    }

    public void  setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}


