package com.alzheimerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alzheimerapp.R;
import com.alzheimerapp.pojo.alert.Alert;

import java.util.List;


public class HomeNotifyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Alert> data;

    public HomeNotifyAdapter(Context context, List<Alert> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_home_notify, parent, false);

        MyHolder holder = new MyHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final Alert g = data.get(position);

        MyHolder myHolder = (MyHolder) holder;

        myHolder.name.setText(g.getName());
        myHolder.date.setText(g.getDateTime());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView date;

        public MyHolder(View itemViewRes) {
            super(itemViewRes);

            name = itemViewRes.findViewById(R.id.name);
            date = itemViewRes.findViewById(R.id.date);


        }


    }


}