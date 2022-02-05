package com.example.deliveryapp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.deliveryapp.R;

public class SearchResultItem extends RecyclerView.Adapter<SearchResultItem.MyViewHolder> {
    private LayoutInflater inflater;
    List<String> list;
    private final Context mContext;

    public SearchResultItem(Context ctx, List<String> list) {
        inflater = LayoutInflater.from(ctx);
        this.mContext = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public SearchResultItem.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_searchresult_items, parent, false);
        SearchResultItem.MyViewHolder holder = new SearchResultItem.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultItem.MyViewHolder holder, int position) {
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.addToCart.setText("Added");
                Drawable img = mContext.getResources().getDrawable(R.drawable.ic_tick);
                holder.addToCart.setCompoundDrawablesWithIntrinsicBounds(img,null,null,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        Button addToCart;
        TextView name;
        TextView price;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.tvName);
            price = (TextView) itemView.findViewById(R.id.tvPrice);
            addToCart = (Button) itemView.findViewById(R.id.btAdd);
        }

    }
}
