package com.alzheimerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alzheimerapp.R;
import com.alzheimerapp.pojo.gallery.GalleryImg;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AlbumPicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<GalleryImg> data;

    public AlbumPicAdapter(Context context, List<GalleryImg> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_pic_person, parent, false);

        MyHolder holder = new MyHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final GalleryImg g = data.get(position);

        MyHolder myHolder = (MyHolder) holder;

        myHolder.username.setText(g.getName());
        myHolder.relation.setText(g.getRelation());

        Picasso.get().load(g.getUrlImg()).into(myHolder.image);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView username;
        TextView relation;
        CircleImageView image;

        public MyHolder(View itemViewRes) {
            super(itemViewRes);

            username = itemViewRes.findViewById(R.id.name);
            relation = itemViewRes.findViewById(R.id.relation);
            image = itemViewRes.findViewById(R.id.image);

        }


    }


}