package com.example.deliveryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.deliveryapp.R;
import com.example.deliveryapp.ShopDetailsList;

import static com.example.deliveryapp.commonutils.Utils.Navigation;

public class TopSearch extends RecyclerView.Adapter<TopSearch.MyViewHolder> {
    private LayoutInflater inflater;
    List<String> list;
    private final Context mContext;

    public TopSearch(Context ctx, List<String> list) {
        inflater = LayoutInflater.from(ctx);
        this.mContext = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public TopSearch.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_top_search, parent, false);
        TopSearch.MyViewHolder holder = new TopSearch.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TopSearch.MyViewHolder holder, int position) {
        holder.store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation(mContext, ShopDetailsList.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView store;
        TextView name;
        TextView timing;

        public MyViewHolder(View itemView) {
            super(itemView);
            store = (ImageView) itemView.findViewById(R.id.ivStore);
            name = (TextView) itemView.findViewById(R.id.tvShopName);
            timing = (TextView) itemView.findViewById(R.id.tvTime);
        }

    }
}