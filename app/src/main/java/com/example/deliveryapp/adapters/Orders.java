package com.example.deliveryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.deliveryapp.R;

public class Orders extends RecyclerView.Adapter<Orders.MyViewHolder> {

    private LayoutInflater inflater;
    List<String> list;
    private final Context mContext;

    public Orders(Context ctx, List<String> list) {
        inflater = LayoutInflater.from(ctx);
        this.mContext = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public Orders.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_orders, parent, false);
        Orders.MyViewHolder holder = new Orders.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Orders.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView status;
        TextView orderId;
        TextView time;

        public MyViewHolder(View itemView) {
            super(itemView);
            status = (TextView) itemView.findViewById(R.id.tvStatus);
            orderId = (TextView) itemView.findViewById(R.id.tvID);
            time = (TextView) itemView.findViewById(R.id.tvTime);
        }
    }

}
