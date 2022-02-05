package com.example.deliveryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

import com.example.deliveryapp.R;
import com.example.deliveryapp.ShopDetailsList;

import static com.example.deliveryapp.commonutils.Utils.Navigation;

public class SearchResult extends RecyclerView.Adapter<SearchResult.MyViewHolder> {
    private LayoutInflater inflater;
    List<String> list;
    private final Context mContext;
    boolean shop;

    public SearchResult(Context ctx, List<String> list) {
        inflater = LayoutInflater.from(ctx);
        this.mContext = ctx;
        this.list = list;
    }
    public SearchResult(Context ctx, List<String> list,boolean shop) {
        inflater = LayoutInflater.from(ctx);
        this.mContext = ctx;
        this.list = list;
        this.shop = shop;
    }

    @NonNull
    @Override
    public SearchResult.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_search_result, parent, false);
        SearchResult.MyViewHolder holder = new SearchResult.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResult.MyViewHolder holder, int position) {
        if (shop) {
            holder.keyResult.setVisibility(View.GONE);
        } else {
            SearchResultItem topSearch = new SearchResultItem(mContext, list);
            holder.keyResult.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.HORIZONTAL));
            holder.keyResult.setAdapter(topSearch);
        }
        holder.name.setOnClickListener(new View.OnClickListener() {
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

        RecyclerView keyResult;
        TextView name;
        TextView timing;

        public MyViewHolder(View itemView) {
            super(itemView);

            keyResult = (RecyclerView) itemView.findViewById(R.id.rv_keyWord);
            name = (TextView) itemView.findViewById(R.id.tvShopName);
            timing = (TextView) itemView.findViewById(R.id.tvDistance);
        }

    }
}
